package com.hcx.asclepiusmanager.sysmgr.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.JwtTokenUtils;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.JwtUser;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUserLoginDTO;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysMenuService;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.hcx.asclepiusmanager.common.utils.JwtTokenUtils.TOKEN_PREFIX;

/**
 * @ClassName AuthController
 * @Description TODO
 * @Author hcx
 * @Date 2022/2/9 15:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response, String codeKey) throws IOException {
        // 定义验证码
        ShearCaptcha captcha = CaptchaUtil.
                createShearCaptcha(100, 38, 4, 2);
        String code = captcha.getCode();

        System.out.println(code);

        // 储存到redis
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(codeKey, code);
        // 设置  缓存时间 60秒
        opsForValue.getOperations().expire(codeKey, 60, TimeUnit.SECONDS);
        // 返回流
        captcha.write(response.getOutputStream());


    }

    @PostMapping("/register")
    public String registerUser(String username, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(bCryptPasswordEncoder.encode(password));
        sysUser.setRole("ROLE_ADMIN");
        return sysUserService.insert(sysUser).toString();
    }

    @PostMapping("/doLogin")
    public Result loginUser(SysUserLoginDTO sysUserLoginDTO) {
//        SysUser sysUser=new SysUser();
//        sysUser.setUsername(loginUser.get("username"));
//        sysUser.setPassword(bCryptPasswordEncoder.encode(loginUser.get("password")));
        //没写完 先假装怎样都能登陆成功
//        sysUser.setRole("ROLE_ADMIN");

        // 获取redis验证码
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String code = forValue.get(sysUserLoginDTO.getKeyCode());

        Authentication authentication = null;

        if (null == code) {
            return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "当前验证码过期请重新获取");
        } else {

            try {
                System.out.println(sysUserLoginDTO.getCaptcha());
                // 进行比较 不考虑大小写
                if (code.equalsIgnoreCase(sysUserLoginDTO.getCaptcha())) {
                    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sysUserLoginDTO.getUsername(), sysUserLoginDTO.getPassword(), new ArrayList<>()));
//
                    JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
                    String role = "";
                    Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
                    for (GrantedAuthority authority : authorities) {
                        role = authority.getAuthority();
                    }
                    String token = JwtTokenUtils.createToken(jwtUser.getUsername(), role, false);
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", TOKEN_PREFIX + token);
                    map.put("username", jwtUser.getUsername());
                    return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), map);
                } else {
                    return new Result(ResultEnum.ERROR.getCode(), "验证码错误", null);
                }
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return new Result(ResultEnum.ERROR.getCode(), "用户名或密码不正确", null);
            }

        }
    }

    /**
     * 验证是否登陆
     *
     * @return
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public Result checkLogin(HttpServletRequest httpServletRequest) {
        // 判断是否认证成功
        String token=httpServletRequest.getHeader("TOKEN");
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            if(!JwtTokenUtils.isExpiration(token.substring(7, token.length()))){
                return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "LOGGED IN");
            }
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "NOT LOGGED IN");
    }

    @GetMapping("/initMenu")
    public Map<String, Object> initMenu(HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("TOKEN");
        String role="";
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            role=JwtTokenUtils.getUserRole(token.substring(7, token.length()));
        }
        return sysMenuService.getMenu(role);
    }

}
