package com.lemon.testcase;

import com.lemon.casepojo.CasePojo;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.encryption.RSAManager;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-13 17:16
 * @Desc：
 **/
public class UserInfoTest extends BaseTest {

    @BeforeClass
    public void setup(){
        String member_phone= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("member_phone",member_phone);
    }

    @Test(dataProvider = "getLoginDatas")
    public void userinfoTest(CasePojo casePojo) throws Exception {
        if (casePojo.getCaseId()>2){
            //获取当前时间戳
            long timeStap = System.currentTimeMillis()/1000;
            //获取token的前50位
            String strToken = (String) Environment.envieomentMap.get("usertoken");
            String token_50=strToken.substring(0,50);
            String str=token_50+timeStap;
            String encryptStr= RSAManager.encryptWithBase64(str);
            Environment.envieomentMap.put("timestamp",timeStap);
            Environment.envieomentMap.put("sign",encryptStr);
        }
        casePojo=paramsReplace(casePojo);
        Response res = request(casePojo);
        extractToEnvironment(casePojo,res);
        //断言
        assertResponse(casePojo,res);

    }
    @DataProvider
    public Object[] getLoginDatas(){
        return ExcelUtils.readExcelAllDatas(5).toArray();
    }

}
