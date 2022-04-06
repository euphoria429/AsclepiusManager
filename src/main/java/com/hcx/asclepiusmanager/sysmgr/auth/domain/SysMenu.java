package com.hcx.asclepiusmanager.sysmgr.auth.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author huangcaixia
 * @date 2022/3/30 11:03
 */
@Data
@TableName("sys_menu")
public class SysMenu {
    private Integer id;
    private String title;
    private String href;
    private String icon;
    private String target;
}
