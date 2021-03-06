package com.hcx.asclepiusmanager.sysmgr.auth.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.BCryptPasswordUtil;
import com.hcx.asclepiusmanager.common.utils.JwtTokenUtils;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.JwtUser;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUser;
import com.hcx.asclepiusmanager.sysmgr.auth.domain.SysUserLoginDTO;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysMenuService;
import com.hcx.asclepiusmanager.sysmgr.auth.service.SysUserService;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.hcx.asclepiusmanager.common.utils.JwtTokenUtils.TOKEN_PREFIX;

/**
 * @ClassName AuthController
 * @Author hcx
 * @Date 2022/2/9 15:07
 * @Version 1.0
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private SysUserService sysUserService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    AuthenticationManager authenticationManager;

    @Resource
    private SysMenuService sysMenuService;

    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse response, String codeKey) throws IOException {
        // ???????????????
        ShearCaptcha captcha = CaptchaUtil.
                createShearCaptcha(100, 38, 4, 2);
        String code = captcha.getCode();

        System.out.println(code);

        // ?????????redis
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set(codeKey, code);
        // ??????  ???????????? 60???
        opsForValue.getOperations().expire(codeKey, 60, TimeUnit.SECONDS);
        // ?????????
        captcha.write(response.getOutputStream());


    }

    @PostMapping("/register")
    public String registerUser(String username, String password) {
        if(sysUserService.findByUsername(username)!=null){
            return "????????????";
        }
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(BCryptPasswordUtil.encryptPassword(
                HmacUtils.hmacSha1Hex(password, "loveChiWah")));
        sysUser.setRole("ROLE_ADMIN");
        return sysUserService.insert(sysUser).toString();
    }

    /**
     * ???????????????????????????????????????????????????123456
     * @param username
     * @return
     */
    @PostMapping("/addAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public Result addAdmin(String username) {
        Integer code=sysUserService.addAdmin(username);
        if (1 == code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "?????????????????????");
        }
        if(-1 == code){
            return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "???????????????");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "?????????????????????");
    }

    /**
     * ????????????
     * TODO ?????????
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @PostMapping("/changePwd")
    @PreAuthorize("hasRole('ADMIN')")
    public Result changePwd(String oldPwd,String newPwd) {
        Integer code=sysUserService.changePwd(oldPwd,newPwd);
        if (0 != code) {
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "??????????????????");
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "??????????????????");
    }

    @PostMapping("/doLogin")
    public Result loginUser(SysUserLoginDTO sysUserLoginDTO) {
        // ??????redis?????????
        ValueOperations<String, String> forValue = redisTemplate.opsForValue();
        String code = forValue.get(sysUserLoginDTO.getKeyCode());

        Authentication authentication = null;

        if (null == code) {
            return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "????????????????????????????????????");
        } else {

            try {
                System.out.println(sysUserLoginDTO.getCaptcha());
                // ???????????? ??????????????????
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
                    map.put("Authorization", TOKEN_PREFIX + token);
                    map.put("username", jwtUser.getUsername());
                    return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), map);
                } else {
                    return new Result(ResultEnum.ERROR.getCode(), "???????????????", null);
                }
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return new Result(ResultEnum.ERROR.getCode(), "???????????????????????????", null);
            }

        }
    }

    /**
     * ??????????????????
     *
     * @return
     */
    @RequestMapping("checkLogin")
    @ResponseBody
    public Result checkLogin(HttpServletRequest httpServletRequest) {
        // ????????????????????????
        String token=httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            if(!JwtTokenUtils.isExpiration(token.substring(7, token.length()))){
                return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "LOGGED IN");
            }
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "NOT LOGGED IN");
    }

    @GetMapping("/initMenu")
    public Map<String, Object> initMenu(HttpServletRequest httpServletRequest){
        String token=httpServletRequest.getHeader("Authorization");
        String role="";
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
            role=JwtTokenUtils.getUserRole(token.substring(7, token.length()));
        }
        return sysMenuService.getMenu(role);
    }

}
