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
 * @Create: 2021-07-20 14:04
 * @Desc：
 **/
public class InvestFlow extends BaseTest {
    @BeforeClass
    public void setup(){
        String admin_phone_register= RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("admin_phone_register",admin_phone_register);
        String common_register_phone=RandomDataUtil.getUnRegisterPhone();
        Environment.envieomentMap.put("common_register_phone",common_register_phone);
    }

    @Test(dataProvider = "getData")
    public void investFlowTest(CasePojo casePojo) {
        casePojo = paramsReplace(casePojo);
        Response res = request(casePojo);
        extractToEnvironment(casePojo, res);
        assertResponse(casePojo,res);
        //数据库断言
        assertDB(casePojo);

    }

    @DataProvider
    public Object[] getData() {
        return ExcelUtils.readExcelAllDatas(3).toArray();
    }

}
