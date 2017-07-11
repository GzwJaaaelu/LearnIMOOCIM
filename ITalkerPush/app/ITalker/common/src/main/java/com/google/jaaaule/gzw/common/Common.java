package com.google.jaaaule.gzw.common;

/**
 * Created by admin on 2017/5/21.
 */

public class Common {

    /**
     * 一些不变的参数
     * 通常用于一些配置
     */
    public interface Constance {
        //  手机号的正则
        String REGEX_PHONE = "[1][3,4,5,7,8][0-9]{9}";
        //  基础网络请求地址
        String API_URL = "http://172.19.82.83:8080/api/";
    }
}
