package com.ifenghui.storybookapi.adminapi.service;


import java.io.InputStream;

public interface ExpressCenterExcelService {

    /**
     * 物流导入excel表
     * @param fileName
     * @param
     */
    Integer trackImport(String fileName, InputStream is) throws Exception;


    /**
     * 订单导入excel表
     * @param fileName
     * @param is
     */
    Integer orderImport(String fileName, InputStream is) throws Exception;

}
