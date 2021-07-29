package com.lemon.common;

import com.alibaba.fastjson.JSONObject;
import com.lemon.casepojo.CasePojo;
import com.lemon.data.Environment;
import com.lemon.utils.Constants;
import com.lemon.utils.JDBCUtils;
import com.sun.xml.bind.v2.model.core.Element;
import com.sun.xml.bind.v2.model.core.ID;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-13 17:52
 * @Desc：公用的父类
 **/
public class BaseTest {
    @BeforeSuite
    public void beforeSuite() throws FileNotFoundException {
        //设置响应结果的小数floatt类型，把json小数的返回类型配置成BigDecimal类型
        RestAssured.config = RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        RestAssured.baseURI = Constants.BASE_URL;
        //全局日志配置，将所有日志保存在文件中（重定向）。如果日志名称相同，会覆盖上一次的日志
        //如果想要不覆盖，可以在文件名称加时间戳
        //如果每次想要每次清空上一次的日志，可以放在target目录下，执行mvn clean会清空上一次的日志
       // PrintStream fileOutPutStream = new PrintStream(new File("test_all.log"));
       // RestAssured.filters(new RequestLoggingFilter(fileOutPutStream), new ResponseLoggingFilter(fileOutPutStream));

    }

    /**
     * 断言
     *
     * @param casePojo 请求数据
     * @param res      响应结果
     */
    public void assertResponse(CasePojo casePojo, Response res) {
        if (casePojo.getExpected() != null) {
            String jsonExpectedDatas = casePojo.getExpected();
            Map<String, Object> mapExpectedDatas = JSONObject.parseObject(jsonExpectedDatas);
            //keyset是无序的
            Set<String> keySet = mapExpectedDatas.keySet();
            for (String key : keySet) {
                Assert.assertEquals(res.jsonPath().get(key), mapExpectedDatas.get(key));
            }
        }
    }

    /**
     * 接口请求的封装
     *
     * @param casePojo 请求数据（实体类）
     * @return 响应结果
     */
    public Response request(CasePojo casePojo) {
        String logPath="";
        if (!Constants.SHOW_CONSOLE_LOG){
            File dirFile=new File("log\\"+casePojo.getSheetName());
            if (!dirFile.exists()){
                dirFile.mkdirs();
            }
            PrintStream fileOutPutStream = null;
            logPath="log\\"+casePojo.getSheetName()+"\\"+casePojo.getModule()+"_"+casePojo.getCaseId()+".log";
            try {
                fileOutPutStream = new PrintStream(new File(logPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            RestAssured.config =
                    RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));

        }
        //获取请求头
        String jsonRequestHeaders = casePojo.getRequestHeader();
        Map<String, Object> mapRequestHeaders = JSONObject.parseObject(jsonRequestHeaders);
        //请求地址
        String requestUtl = casePojo.getUrl();
        //获取请求参数
        String requestParams = casePojo.getInputParams();
        Response res = null;
        if ("get".equalsIgnoreCase(casePojo.getMethod())) {
            //如果是get请求，需要传参的华直接拼接在接口地址后面
            if (requestParams != null) {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).get(requestUtl + "?" + requestParams).then().log().all().extract().response();
            } else {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).get(requestUtl).then().log().all().extract().response();
            }

        } else if ("post".equalsIgnoreCase(casePojo.getMethod())) {
            if (requestParams != null) {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).body(requestParams).post(requestUtl).then().log().all().extract().response();
            } else {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).post(requestUtl).then().log().all().extract().response();
            }
        } else if ("patch".equalsIgnoreCase(casePojo.getMethod())) {
            if (requestParams != null) {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).body(requestParams).patch(requestUtl).then().log().all().extract().response();
            } else {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).patch(requestUtl).then().log().all().extract().response();
            }
        } else if ("delete".equalsIgnoreCase(casePojo.getMethod())) {
            if (requestParams != null) {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).body(requestParams).delete(requestUtl).then().log().all().extract().response();
            } else {
                res = RestAssured.given().log().all().headers(mapRequestHeaders).delete(requestUtl).then().log().all().extract().response();
            }
        }
        //请求之后将日志添加到allure报表中
        //这里执行添加XX信息到日志文件中（logFilepath）
        //请求结束之后将接口日志添加到allure报表中
        if (!Constants.SHOW_CONSOLE_LOG){
            try {
                Allure.addAttachment("接口请求响应信息",new FileInputStream(logPath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 通过提取表达式将对应的响应值保存到环境变量中
     *
     * @param casePojo
     * @param res
     */
    public void extractToEnvironment(CasePojo casePojo, Response res) {
        if (casePojo.getExtract() != null) {
            String extractExpr = casePojo.getExtract();
            Map<String, Object> extractExprMap = JSONObject.parseObject(extractExpr);
            Set<String> keySet = extractExprMap.keySet();
            for (String key : keySet) {
                Object value = res.jsonPath().get((String) extractExprMap.get(key));
                Environment.envieomentMap.put(key, value);
            }
        }
    }

    /**
     * 字符串正则匹配替换
     *
     * @param orgStr
     * @return
     */
    public String regexReplace(String orgStr) {
        if (orgStr != null) {
            //匹配器
            Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
            //匹配对象
            Matcher matcher = pattern.matcher(orgStr);
            String result = orgStr;
            //循环遍历匹配对象
            while (matcher.find()) {
                //获取整个匹配正则的字符串
                String allFindStr = matcher.group(0);
                //获取{{xx}}内部匹配到的字符串
                String innerStr = matcher.group(1);
                //从环境变量获取值进行替换
                Object value = Environment.envieomentMap.get(innerStr);
                result = result.replace(allFindStr, value + "");
            }
            return result;
        } else {
            return orgStr;
        }
    }

    /**
     * 整条用例的参数化设计，只要用例中有符合条件的匹配就会进行替换，没有则不会替换
     *
     * @param casePojo
     */
    public CasePojo paramsReplace(CasePojo casePojo) {
        //请求头
        String requestHeader = casePojo.getRequestHeader();
        casePojo.setRequestHeader(regexReplace(requestHeader));
        //请求参数
        String inputParams = casePojo.getInputParams();
        casePojo.setInputParams(regexReplace(inputParams));
        //请求地址
        String url = casePojo.getUrl();
        casePojo.setUrl(regexReplace(url));
        //期望结果
        String expected = casePojo.getExpected();
        casePojo.setExpected(regexReplace(expected));
        //数据库断言参数替换
        String dbAssert = casePojo.getDbAssert();
        casePojo.setDbAssert(regexReplace(dbAssert));
        return casePojo;

    }


    //jdbc读取不通数据对应的返回类型
    //1.数据库字段字符串类型====》jdbc实际返回的类型 字符串 BigDecimal string
    //2.小数类型----BigDecimal easypoi====BigDecimal
    //3.整数类型=====Long    easypoi====Integer

    /**
     * 数据库断言封装
     *
     * @param casePojo
     */
    public void assertDB(CasePojo casePojo) {
        if (casePojo.getDbAssert() != null) {
            String dbAssert = casePojo.getDbAssert();
            Map<String, Object> mapDBAssert = JSONObject.parseObject(dbAssert);
            Set<String> keySet = mapDBAssert.keySet();
            for (String key : keySet) {
                Object dbActual = JDBCUtils.querySingleData(key);
                //根据数据库中读取的实际返回类型来做判断
                if (dbActual instanceof Long) {
                    Integer actualExpected = (Integer) mapDBAssert.get(key);
                    long expected = actualExpected.longValue();
                    Assert.assertEquals(dbActual, expected);
                } else {
                    Object expected = mapDBAssert.get(key);
                    Assert.assertEquals(dbActual, expected);
                }

            }
        }


    }
}
