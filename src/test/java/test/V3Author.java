package test;

import com.lemon.encryption.RSAManager;
import com.lemon.utils.RandomDataUtil;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-27 11:52
 * @Desc：
 **/
public class V3Author {
    @Test
    public  void  registerLoginRechargeTest() throws Exception {
        //1.注册
        //生成随机手机号
        String mobile_phone= RandomDataUtil.getUnRegisterPhone();
        String registerData="{\"mobile_phone\":\""+mobile_phone+"\",\"pwd\":\"12345678\"}";
        Response registerRes= RestAssured.
                given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                contentType("application/json").body(registerData).
                when().
                post("http://api.lemonban.com/futureloan/member/register").
                then().
                log().all().extract().response();
        String phone=null;
        if (registerRes.jsonPath().get("data")!=null){
            phone=registerRes.jsonPath().get("data.mobile_phone");
        }
        //2.登录
        String loginData="{\"mobile_phone\":\""+phone+"\",\"pwd\":\"12345678\"}";
        Response loginRes=RestAssured.
                given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                contentType("application/json").body(loginData).
                when().
                post("http://api.lemonban.com/futureloan/member/login").
                then().
                log().all().extract().response();
        String loginToken=null;
        int memberId = 0;
        if (loginRes.jsonPath().get("data")!=null){
            loginToken=loginRes.jsonPath().get("data.token_info.token");
            memberId=loginRes.jsonPath().get("data.id");
        }
        //3.充值
        //获取当前时间戳
        long timeStap = System.currentTimeMillis()/1000;
        //获取token的前50位
        String strToken = loginToken.substring(0, 50);
        String str=strToken+timeStap;
        String encryptStr=RSAManager.encryptWithBase64(str);

        String rechargeData="{\"member_id\":"+memberId+",\"amount\":10000.0,\"timestamp\":"+timeStap+",\"sign\":\""+encryptStr+"\"}";
        RestAssured.given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                header("Authorization","Bearer "+loginToken).
                contentType("application/json").
                body(rechargeData).
                when().
                post("http://api.lemonban.com/futureloan/member/recharge").
                then().
                log().all().extract().response();
    }


}
