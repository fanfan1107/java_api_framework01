package com.lemon.testcase;

import com.alibaba.fastjson.JSONObject;
import com.lemon.casepojo.CasePojo;
import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
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
 * @Create: 2021-07-13 16:00
 * @Desc：
 **/
public class LoginTest extends BaseTest {
    @BeforeTest
    public void beforeTest() {
        String login_register_phone= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("login_register_phone",login_register_phone);
        //登录的前置：注册账号
        List<CasePojo> casePojoList = ExcelUtils.readExcelSpecifyDatas(2, 1, 1);
        paramsReplace(casePojoList.get(0));
        Response res = request(casePojoList.get(0));
        //保存到map中
        extractToEnvironment(casePojoList.get(0),res);

    }

    @Test(dataProvider = "getLoginDatas")
    public void loginTest(CasePojo casePojo) {
        paramsReplace(casePojo);
        Response res = request(casePojo);
        //断言
        assertResponse(casePojo, res);

    }

    @DataProvider
    public Object[] getLoginDatas() {
        return ExcelUtils.readExcelSpecifyDatas(2, 2).toArray();
    }
}
