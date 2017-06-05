package com.inroad.apitest.concurrency;

import com.inroad.apitest.common.SendPostRequest;
import com.inroad.apitest.scan.GetResponseAndCode;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Created by shishuaigang on 2017/6/2.
 * 并发测试的代码
 */

public class ConcurrencyCore {

    private String url;
    private int concurrencyTimes;
    private String cookie;
    private String param;

    public String getUrl() {
        return url;
    }

    public int getConcurrencyTimes() {
        return concurrencyTimes;
    }

    public String getCookie() {
        return cookie;
    }

    public String getParam() {
        return param;
    }

    public ConcurrencyCore(String url, int times, String param, String cookie) {
        this.url = url;
        this.concurrencyTimes = times;
        this.param = param;
        this.cookie = cookie;
    }

    public ConcurrentLinkedDeque concurrency() throws InterruptedException, ExecutionException {

        ConcurrentLinkedDeque<ArrayList> cld = new ConcurrentLinkedDeque<>();
        //线程数设为8,cpu为双核4线程
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5, new WorkThreadFactory());

        //并发次数从输入框中获取
        for (int i = 0; i < concurrencyTimes; i++) {
            Future<ArrayList> future = fixedThreadPool.submit(new Callable<ArrayList>() {
                @Override
                public ArrayList call() throws Exception {
                    long begin = System.nanoTime();
                    SendPostRequest re = new SendPostRequest(getUrl(), getParam(), getCookie());
                    HttpURLConnection fanhui = re.Post();//发送post请求，return conn
                    //fanhui.getResponseCode();
                    Long end = System.nanoTime();
                    System.out.println((end-begin));
                    ArrayList<String> response = new GetResponseAndCode(fanhui).getRes();
                    return response; //return，这样future才能get
                }
            });
            cld.add(future.get());
        }
        fixedThreadPool.shutdown();
        return cld;
    }
}
