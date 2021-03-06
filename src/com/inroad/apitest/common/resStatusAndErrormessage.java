package com.inroad.apitest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * responseStatus方法返回response中的status的值
 * 如果遇到500返回，则返回-1
 * ###########
 * responseErrorMessage方法返回response中的error中的message的值
 * 如果遇到500返回，则返回"无正确的response,有可能为500错误,请检查"
 */

public class resStatusAndErrormessage {

    private String jsondata;

    public resStatusAndErrormessage(String Json) {
        this.jsondata = Json;
    }

    // 读取response message中的status
    // 如果api返回成功,则读取status
    // 如果api返回失败,则返回-1
    public int responseStatus() {
        int status;
        if (jsondata.endsWith("}")) {
            JSONObject jsonob = JSON.parseObject(jsondata);
            status = jsonob.getInteger("status");
        } else {
            status = -1;
        }
        return status;
    }

    //读取response message中的error message
    // 如果api返回成功,则读取message
    // 如果api返回失败,则返回"无正确的response,有可能为500错误,请检查"
    public String responseErrorMessage() {
        String message;
        if (jsondata.endsWith("}")) {
            JSONObject jsonob = JSON.parseObject(jsondata);
            message = JSON.parseObject(jsonob.getString("error")).getString("message");
        } else {
            message = jsondata;
        }
        return message;
    }
}