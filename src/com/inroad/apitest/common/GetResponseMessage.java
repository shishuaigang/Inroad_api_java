package com.inroad.apitest.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;


public class GetResponseMessage {

    private HttpURLConnection connection;

    public GetResponseMessage(HttpURLConnection conn) {
        this.connection = conn;
    }

    public String getResMessage() {
        BufferedReader in = null;
        String res_message = "";
        String result = "";
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                res_message = result;  //返回api的response message
            }
        } catch (IOException e) {
            res_message = null;  //失败测返回null
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res_message;
    }
}
