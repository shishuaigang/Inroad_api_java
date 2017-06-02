package com.inroad.apitest.scan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * 获取api的response的内容和response code
 * 返回的是arraylist
 * 第一个是response code
 * 第二个是api的返回内容，如遇500之类的错误则返回null
 */

public class GetResponseAndCode {

    private HttpURLConnection connection;

    public GetResponseAndCode(HttpURLConnection conn) {
        this.connection = conn;
    }

    public ArrayList<String> getRes() {
        BufferedReader in = null;
        String result = "";
        ArrayList<String> results = new ArrayList<>();
        int code;
        try {
            code = this.connection.getResponseCode();
            results.add(String.valueOf(code));  //返回code
            in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                results.add(result);  //返回api的response message
            }
        } catch (IOException e) {
            results.add(null);  //失败测返回null
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return results;
    }
}
