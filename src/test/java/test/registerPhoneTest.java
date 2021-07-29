package test;

import com.lemon.utils.JDBCUtils;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-20 16:01
 * @Desc：
 **/
public class registerPhoneTest {
    /**
     *
     * @return 返回member中最大的手机号加1
     */
    public static Object getRegisterPhone(){
        //获取到member表中手机号的最大值
        String sql="SELECT mobile_phone FROM member ORDER BY mobile_phone DESC LIMIT 1";
        Object data = JDBCUtils.querySingleData(sql);
        long phone = Long.parseLong(data+"");
        return phone+1;
    }

    
    public static void main(String[] args) {
        Object phone = getRegisterPhone();
        System.out.println(phone);
    }

}
