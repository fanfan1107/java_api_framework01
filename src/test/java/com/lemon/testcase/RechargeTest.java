package com.lemon.testcase;

import com.lemon.casepojo.CasePojo;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.encryption.RSAManager;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-18 17:23
 * @Desc：
 **/
public class RechargeTest extends BaseTest {
    @BeforeTest
    public void beforeTest() {
        String recharge_phone_register= RandomDataUtil.getTel();
        Environment.envieomentMap.put("recharge_phone_register",recharge_phone_register);



    }

    @Test(dataProvider = "getRechargeData")
    public void rechargeTest(CasePojo casePojo) throws Exception {
        if (casePojo.getCaseId()>2){
            v3Author();
        }
        casePojo=paramsReplace(casePojo);
        Response res = request(casePojo);
        extractToEnvironment(casePojo,res);
        //生成member表中没有的用户id
        Object unregister_id = RandomDataUtil.getUnregisterId();
        Environment.envieomentMap.put("unregister_id",unregister_id);
        //断言
        assertResponse(casePojo,res);
        //数据库断言
        assertDB(casePojo);



    }

    @DataProvider
    public Object[] getRechargeData(){
        List<CasePojo> casePojoList = ExcelUtils.readExcelAllDatas(4);
        return casePojoList.toArray();
    }


    public void v3Author(){
        //得到时间戳timestamp(接口要求是：秒级的时间戳)
        long timestamp = System.currentTimeMillis()/1000;
        //sign参数获取（取 token 前 50 位再拼接上 timestamp 值，然后通过 RSA 公钥加密得到字符串）
        String tokenValue = (String)Environment.envieomentMap.get("rechargetoken");
        String token_50 = tokenValue.substring(0,50);
        String str = token_50+timestamp;
        //加密代码--》找开发去获取-->一般是一个jar包
        //使用加密包里面加密算法（RSA）
        String encryptStr = null;
        try {
            encryptStr = RSAManager.encryptWithBase64(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Environment.envieomentMap.put("sign",encryptStr);
        Environment.envieomentMap.put("timestamp",timestamp);
    }
}
