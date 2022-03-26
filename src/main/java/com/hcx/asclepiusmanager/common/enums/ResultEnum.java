package com.hcx.asclepiusmanager.common.enums;

import lombok.Getter;

/**
 * @author huangcaixia
 * @Description TODO
 * @date 2022/3/24 11:55
 */

@Getter
public enum ResultEnum {
    /**
     * 返回枚举类型，每一个枚举类型代表一个返回状态
     * 1** 信息，服务器收到请求，需要请求这继续执行操作
     * 2** 成功，操作被成功执行并接受处理
     * 3** 重定向，需要进一步的操作已完成请求
     * 4** 客户端错误，请求包汉语发错误或无法完成请求
     * 5** 服务器错误，服务在处理请求过程中发生了错误
     */
    SUCCESS(200, "操作成功！"),
    ERROR(400, "请求错误！"),

    PARAMS_ERROR(401, "参数错误！"),
    PARAMS_NULL_ERROR(402, "参数为空错误！"),
    DATA_NOT_FOUND(406, "查询失败！"),
    NOT_LOGIN(410, "账号未登陆！"),
    FORBIDDEN(403, "无权限，禁止访问"),
    UNAUTHORIZED(501, "未授权"),
    NOT_FOUND(404, "访问连接不存在"),
    NOT_SUPPORTED(405, "方法不支持该请求方式"),
    BAD_SQL(407, "发现SQL注入，包含非法字段"),

    FILE_EMPTY_ERROR(701, "上传文件为空"),
    FILE_TYPE_ERROR(702, "文件格式异常，请上传.xls或者.xlsx文件"),

    BAD_CREDENTIALS(801, "用户名密码错误"),

    SERVICE_ERROR(500, "服务在处理请求过程中发生了错误"),
    SERVICE_ERROR2(409, "这条记录已经处理啦，请你刷新一下页面");
    /**
     * code 响应码
     * msg 响应信息
     */
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

