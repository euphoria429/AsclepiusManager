package com.hcx.asclepiusmanager.sysmgr.auth.domain;

import lombok.Data;

import java.util.List;

/**
 * @author huangcaixia
 * @date 2022/3/30 11:51
 */
@Data
public class TopMenu {
    private String title;
    private String href;
    private String icon;
    private String target;
    private List<MenuVO> child;
}
