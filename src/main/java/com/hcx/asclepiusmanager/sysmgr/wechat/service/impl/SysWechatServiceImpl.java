package com.hcx.asclepiusmanager.sysmgr.wechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.gson.Gson;
import com.hcx.asclepiusmanager.common.utils.JwtTokenUtils;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.WeChatSession;
import com.hcx.asclepiusmanager.sysmgr.wechat.mapper.SysWechatMapper;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.hcx.asclepiusmanager.sysmgr.wechat.config.wxConfig.APPID_AUTHORIZED;
import static com.hcx.asclepiusmanager.sysmgr.wechat.config.wxConfig.APPLET_SECRET_AUTHORIZED;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/26 19:55
 */
@Service
@Slf4j
public class SysWechatServiceImpl implements SysWechatService {
    @Resource
    SysWechatMapper sysWechatMapper;

    /**
     * 小程序用户授权登录接口，获取openId和jwt
     *
     * @param code
     * @return
     */
    @Override
    public Map<String, String> authorizedLogin(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID_AUTHORIZED +
                "&secret=" + APPLET_SECRET_AUTHORIZED + "&js_code=" + code + "&grant_type=authorization_code";
        log.info("login url: " + url);

        RestTemplate restTemplate = new RestTemplate();
        //进行网络请求,访问url接口
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //根据返回值进行后续操作
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            if (sessionData.contains("40029")) {
                log.warn("获取openId失败");
            } else {
                Gson gson = new Gson();
                //解析从微信服务器获得的openid和session_key;
                WeChatSession weChatSession = gson.fromJson(sessionData, WeChatSession.class);
                log.info("wechat returned sessions:" + weChatSession.toString());

                //获取用户的唯一标识
                String openId = weChatSession.getOpenid();
                //获取会话秘钥
                String sessionKey = weChatSession.getSession_key();
                //如存在则生成jwt
                String jwt = checkOrCreateNewUserForAuthorized(openId);
                Map<String, String> map = new HashMap<>();
                map.put("openId", openId);
                map.put("sessionKey", sessionKey);
                map.put("jwt", jwt);
                return map;
            }
        }
        return null;
    }

    /**
     * 更新用户信息
     *
     * @param sysWechat
     * @return
     */
    @Override
    public SysWechat updateUserInfo(SysWechat sysWechat) {
        //获取用户当前openid
        String openid=getCurrentOperator();
        if(StringUtils.isBlank(openid)){
            return null;
        }
        sysWechat.setOpenId(openid);
        //根据openid查询
        QueryWrapper<SysWechat> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", sysWechat.getOpenId());
        SysWechat user = sysWechatMapper.selectOne(wrapper);
        user.setAvatarUrl(sysWechat.getAvatarUrl());
        user.setGender(sysWechat.getGender());
        user.setNickName(sysWechat.getNickName());
        sysWechatMapper.updateById(user);
        return user;
    }

    @Override
    public SysWechat getUserInfo() {
        //获取用户当前openid
        String openid=getCurrentOperator();
        if(StringUtils.isBlank(openid)){
            return null;
        }
        //根据openid查询
        QueryWrapper<SysWechat> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openid);
        SysWechat user = sysWechatMapper.selectOne(wrapper);
        return user;
    }

    /**
     * 获取当前用户openid
     *
     * @return
     */
    public String getCurrentOperator() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal().toString();

    }

    /**
     * 检查系统中是否存在openid，无则创建新用户
     *
     * @param openId
     * @return
     */
    private String checkOrCreateNewUserForAuthorized(String openId) {
        SysWechat sysWechat = sysWechatMapper.getWechatUserByOpenId(openId);
        if (null != sysWechat) {
            return JwtTokenUtils.createToken(openId, "ROLE_ADMIN", true);
        } else {
            SysWechat user = createWechatUser(openId);
            return JwtTokenUtils.createToken(openId, "ROLE_ADMIN", true);
        }
    }

    /**
     * 创建新用户
     * @param openId
     * @return
     */
    private SysWechat createWechatUser(String openId) {
        SysWechat sysWechat = new SysWechat();
        sysWechat.setOpenId(openId);
        sysWechatMapper.insert(sysWechat);
        return sysWechat;
    }
}
