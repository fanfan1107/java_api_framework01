package com.lemon.testcase;

import com.lemon.casepojo.CasePojo;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.utils.ExcelUtils;
import com.lemon.utils.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-13 15:36
 * @Desc：
 **/
public class RegisterTest extends BaseTest {
    @BeforeClass
    public void setup(){
        //随机生成未注册的手机号
        String register_phone= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("register_phone",register_phone);
        String register_phone2= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("register_phone2",register_phone2);
        String register_phone_3= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("register_phone_3",register_phone_3);
        String register_phone_4= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("register_phone_4",register_phone_4);
        String register_phone_5= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("register_phone_5",register_phone_5);



    }

    @Test(dataProvider = "getRegisterDatas")
    public void registerTest(CasePojo casePojo){
        //替换
        casePojo = paramsReplace(casePojo);
        Response response = request(casePojo);
        assertResponse(casePojo,response);
        //数据库断言
        System.out.println(casePojo.getDbAssert());
        assertDB(casePojo);

    }

    @DataProvider
    public Object[] getRegisterDatas(){
        return ExcelUtils.readExcelAllDatas(1).toArray();
    }


}
