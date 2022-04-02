package com.hcx.asclepiusmanager.common.utils;

import java.util.UUID;

/**
 * @author huangcaixia
 * @Description 文件处理相关的静态方法
 * @date 2022/3/27 22:39
 */
public class CustFileUtils {

    public static final String EXCEL_SUFFIX = ".xlsx";

    //构造文件名唯一标识id
    public static String generateFileNameUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    //获取文件后缀名
    public static String getSuffix(String originalFilename) {
        int lastIndex = originalFilename.lastIndexOf(".");
        return originalFilename.substring(lastIndex);
    }

}
