package com.lemon.utils;

import com.lemon.data.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-17 14:32
 * @Desc：
 **/
public class RegexTest {
    public static void main(String[] args) {
        String orgStr="{\n" +
                "\"SELECT leave_amount from member WHERE id={{memberid}};\":{{leavemonut}}+10000.00\n" +
                "\n" +
                "}";
        String regexExpr="{{(.*?)}}";
        //匹配器
        Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
        //匹配对象
        Matcher matcher = pattern.matcher(orgStr);
        String result=orgStr;
        //循环遍历匹配对象
        while (matcher.find()){
            //获取整个匹配正则的字符串
            String allFindStr = matcher.group(0);
            //获取{{xx}}内部匹配到的字符串
            String innerStr = matcher.group(1);
            //从环境变量这获取值
            Environment.envieomentMap.put("memberid","183612");
            Environment.envieomentMap.put("leavemonut",10000.00);
            Object value = Environment.envieomentMap.get(innerStr);
            result = result.replace(allFindStr, value + "");
            System.out.println(result);

        }
    }

}
