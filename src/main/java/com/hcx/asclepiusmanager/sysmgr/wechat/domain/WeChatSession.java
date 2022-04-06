package com.hcx.asclepiusmanager.sysmgr.wechat.domain;

import lombok.Data;

/**
 * @author huangcaixia
 * @date 2022/3/26 20:10
 */
@Data
public class WeChatSession {
    public String openid;
    public String session_key;
}
