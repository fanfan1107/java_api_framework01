package com.lemon.utils;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-13 21:51
 * @Desc：
 **/
public class Constants {
    public static final String PROJECT_URL = "api.lemonban.com";
    public static final String BASE_URL = "http://" + PROJECT_URL + "/futureloan";
//    public static final String BASE_URL="https://shaoer.yikeweiqi.com/api";
    public static final String EXCEL_PATH = "src/test/resources/api_testcases_futureloan _v2.xls";

    public static final String DB_URL = "jdbc:mysql://api.lemonban.com/futureloan?useUnicode=true&characterEncoding=utf-8";
    public static final String DB_USER = "future";
    public static final String DB_PASSWORD = "123456";
    //控制台日志输出开关（true表示输出）
    public static final boolean SHOW_CONSOLE_LOG=true;
}