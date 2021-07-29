package com.lemon.utils;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-22 14:18
 * @Desc：
 **/
public class RandomDataUtil {
    public static int getNum(int start,int end) {
        //Math.random()获取0.0-1.0之间的double类型的小数，包头不包尾
        //随机获取start 到 end之间的整数
        return (int)(Math.random()*(end-start+1)+start);
    }
    /**
     * 返回手机号码
     */
    public static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    public static String getTel() {
        System.out.println(telFirst.length);
        //随机获取0到18的数字
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];//随机获取手机号的任意一个号段

        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    //生成没有注册的手机号
    public static String getUnRegisterPhone(){
        String phone=" ";
        //查询数据库是否存在
       while (true){
           phone=getTel();
           Long result =(Long)JDBCUtils.querySingleData("SELECT COUNT(*) FROM member WHERE mobile_phone='" + phone + "';");
           if (1==result){
               continue;
           }else {
               break;
           }
       }
       return phone;

    }

    //生成member表中没有注册过id
    public static Object getUnregisterId(){
        //获取到member表中手机号的最大值
        String sql="SELECT id FROM member ORDER BY id DESC LIMIT 1";
        Object data = JDBCUtils.querySingleData(sql);
        long id = Long.parseLong(data+"");
        return (id+1);
    }

    public static void main(String[] args) {
//        String unRegisterPhone = getUnRegisterPhone();
//        System.out.println(unRegisterPhone);
        Object unregisterId = getUnregisterId();
        System.out.println(unregisterId);
    }

}
