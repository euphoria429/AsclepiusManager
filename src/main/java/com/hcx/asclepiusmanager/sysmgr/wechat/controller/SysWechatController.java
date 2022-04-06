package com.hcx.asclepiusmanager.sysmgr.wechat.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hcx.asclepiusmanager.common.enums.ResultEnum;
import com.hcx.asclepiusmanager.common.utils.Result;
import com.hcx.asclepiusmanager.sysmgr.wechat.domain.SysWechat;
import com.hcx.asclepiusmanager.sysmgr.wechat.service.SysWechatService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author huangcaixia
 * @date 2022/3/26 19:55
 */
@RestController
@RequestMapping("/sysWechat")
public class SysWechatController {

    @Resource
    SysWechatService sysWechatService;

    /**
     * 小程序用户：微信授权登录
     * @param code
     * @return
     */
    @GetMapping("/authorizedLogin")
    @ResponseBody
    public Result authorizedLogin(@RequestParam("code") String code) {
        if(StringUtils.isBlank(code)){
            return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(),"code不能为空");
        }
        Map results = sysWechatService.authorizedLogin(code);
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), results);
    }

    /**
     * 小程序用户：更新用户个人信息
     * @param sysWechat
     * @return
     */
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody SysWechat sysWechat){
        SysWechat user=sysWechatService.updateUserInfo(sysWechat);
        if(user.getId()!=null&&user.getId()!=0){
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), user);
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "用户信息更新失败");
    }

    /**
     * 小程序用戶：获取用户个人信息
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(){
        SysWechat user=sysWechatService.getUserInfo();
        if(user.getId()!=null&&user.getId()!=0){
            return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), user);
        }
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg(), "获取用户信息失败");
    }
}
