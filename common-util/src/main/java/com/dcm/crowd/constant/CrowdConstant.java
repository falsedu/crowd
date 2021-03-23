package com.dcm.crowd.constant;

public class CrowdConstant {
    public static final String ATTR_NAME_EXCEPTION ="exception";
    public static final String MESSAGE_LOGIN_FAILED="抱歉，登录失败";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE="抱歉，这个账号已经被使用了";

    public static final String MESSAGE_ACCESS_FORBIDEN="请登录后再访问";
    public static final String MESSAGE_STRING_IVALIDATE = "字符串不合法，请不要传入空字符串";
    public static final String ATTR_NAME_LOGIN_ADMIN = "admin";
    public static final String MESSAGE_SYSTEM_LOGIN_NOT_UNIQUE = "系统错误，系统库中登陆账号不唯一";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String REDIS_CODE_PREFIX = "code_";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final Object MESSAGE_CODE_NOT_EXISTS = "验证码已经过期，请重新发送";
    public static final Object MESSAGE_CODE_NOT_CORRECT = "验证码不正确，请重新输入";
}
