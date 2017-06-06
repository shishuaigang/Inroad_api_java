package com.inroad.apitest.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class GetResponseCode {
    private HttpURLConnection connection;

    public GetResponseCode(HttpURLConnection conn) {
        this.connection = conn;
    }

    //返回response code
    public int getResCode() {
        int code = 0;
        try {
            code = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }
}
