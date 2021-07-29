package com.lemon.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.casepojo.CasePojo;

import java.io.File;
import java.util.List;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-13 15:56
 * @Desc：
 **/
public class ExcelUtils {
    /**
     *读取excel除表头外的全部的数据
     * @param sheetNumber 从1开始
     * @return
     */
    public static List<CasePojo> readExcelAllDatas(int sheetNumber){
        File file=new File(Constants.EXCEL_PATH);
        //读取或则导入excel的一些参数配置
        ImportParams importParams=new ImportParams();
        importParams.setStartSheetIndex(sheetNumber-1);
        return ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
    }


    /***
     * 读取excel中指定行数的数据
     * @param sheetNumber  第几个sheet
     * @param startRows  从第几行开始读取
     * @param readRows    要读取的行数
     * @return
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNumber,int startRows,int readRows){
        File file=new File(Constants.EXCEL_PATH);
        //读取或则导入excel的一些参数配置
        ImportParams importParams=new ImportParams();
        importParams.setStartSheetIndex(sheetNumber-1);//从0开始
        //设置读取开始行，从0开始
        importParams.setStartRows(startRows-1);
        //读取行数有多少行
        importParams.setReadRows(readRows);

        return ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
    }

    /**
     * excel中从指定行开始读取，一直读取到最后
     * @param sheetNumber
     * @param startRows
     * @return
     */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNumber,int startRows){
        File file=new File(Constants.EXCEL_PATH);
        //读取或则导入excel的一些参数配置
        ImportParams importParams=new ImportParams();
        importParams.setStartSheetIndex(sheetNumber-1);//从0开始
        //设置读取开始行，从0开始
        importParams.setStartRows(startRows-1);
        return ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
    }

}
