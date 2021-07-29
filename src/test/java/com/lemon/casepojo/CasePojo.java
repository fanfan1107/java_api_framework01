package com.lemon.casepojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Project: java_api
 * @Author: fanfan
 * @Create: 2021-07-10 16:48
 * @Desc：实体类映射excel中的表头
 **/
public class CasePojo {
    @Excel(name = "序号(caseId)")
    private int caseId;

    @Excel(name = "接口模块(module)")
    private String module;

    @Excel(name = "用例标题(title)")
    private String title;

    @Excel(name = "请求头(requestHeader)")
    private String requestHeader;

    @Excel(name = "请求方式(method)")
    private String method;

    @Excel(name = "接口地址(url)")
    private String url;

    @Excel(name = "参数输入(inputParams)")
    private String inputParams;

    @Excel(name = "期望返回结果(expected)")
    private String expected;
    @Excel(name = "提取表达式(extract)")
    private String extract;
    @Excel(name = "数据库断言(dbAssert)")
    private String dbAssert;
    @Excel(name = "sheet名称(sheetName)")
    private String sheetName;

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getDbAssert() {
        return dbAssert;
    }

    public void setDbAssert(String dbAssert) {
        this.dbAssert = dbAssert;
    }

    public CasePojo(int caseId, String module, String title, String requestHeader, String method, String url, String inputParams, String expected, String extract, String dbAssert, String sheetName) {
        this.caseId = caseId;
        this.module = module;
        this.title = title;
        this.requestHeader = requestHeader;
        this.method = method;
        this.url = url;
        this.inputParams = inputParams;
        this.expected = expected;
        this.extract = extract;
        this.dbAssert = dbAssert;
        this.sheetName = sheetName;
    }

    public CasePojo(){

    }

    @Override
    public String toString() {
        return "CasePojo{" +
                "caseId=" + caseId +
                ", module='" + module + '\'' +
                ", title='" + title + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", inputParams='" + inputParams + '\'' +
                ", expected='" + expected + '\'' +
                ", extract='" + extract + '\'' +
                ", dbAssert='" + dbAssert + '\'' +
                ", sheetName='" + sheetName + '\'' +
                '}';
    }
}
