package com.inroad.apitest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * calculateResponseTime方法返回服务器处理api的时间
 * 使用backtime与sendtime的差值，如果两者相等，则返回1ms
 * ###########
 * responseStatus方法返回response中的status的值
 * 如果遇到500返回，则返回-1
 * ###########
 * responseErrorMessage方法返回response中的error中的message的值
 * 如果遇到500返回，则返回"无正确的response,有可能为500错误,请检查"
 */

public class resTimeAndStatusAndMessage {

    private String jsondata;

    public resTimeAndStatusAndMessage(String Json) {
        this.jsondata = Json;
    }

    // 计算server的response time
    // 如果api返回成功,使用backtime与sendtime的差值,(相等,则显示<1ms)
    // 如果api返回失败,时间显示NA
    public String calculateResponseTime() {
        JSONObject jsonob = JSON.parseObject(this.jsondata);

        long sendmsec = 0;//毫秒
        long backmsec = 0;//毫秒

        String tIme = "";
        if (jsonob != null) {
            String st = jsonob.getString("sendtime");
            String bt = jsonob.getString("backtime");

            if (st != null && bt != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");

                try {
                    sendmsec = sdf.parse(st).getTime();
                    backmsec = sdf.parse(bt).getTime();
                    if (sendmsec == backmsec) {
                        tIme = "<" + String.valueOf(1);
                    } else {
                        tIme = String.valueOf(backmsec - sendmsec);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else {
            tIme = "NA";
        }
        return tIme;
    }

    // 读取response message中的status
    // 如果api返回成功,则读取status
    // 如果api返回失败,则返回-1
    public int responseStatus() {
        JSONObject jsonob = JSON.parseObject(this.jsondata);
        int status;
        if (jsonob != null) {
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
        JSONObject jsonob = JSON.parseObject(this.jsondata);
        String message;
        if (jsonob != null) {
            message = JSON.parseObject(jsonob.getString("error")).getString("message");
        } else {
            message = "无正确的response,有可能为500错误,请检查";
        }
        return message;
    }
}
