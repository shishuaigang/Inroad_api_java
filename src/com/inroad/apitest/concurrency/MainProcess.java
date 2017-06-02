package com.inroad.apitest.concurrency;

import com.inroad.apitest.common.GetCookie;
import com.inroad.apitest.common.Params;
import com.inroad.apitest.common.SendPostRequest;
import com.inroad.apitest.scan.GetResponseAndCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by shishuaigang on 2017/5/31.
 * 并发测试的流程
 */

public class MainProcess {

    private String PATH;

    public MainProcess(String jsonfolderPATH) {
        this.PATH = jsonfolderPATH;
    }

    public void mainProcess() throws Exception {
        Date d = new Date();
        SimpleDateFormat bt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String begintime = bt.format(d);// 测试开始的时间
        SimpleDateFormat fn = new SimpleDateFormat("yyyyMMddHHmmss");
        String foldername = fn.format(d);// 存放报告的文件夹的名字(时间戳命名)

        //登录,获取cookie
        GetCookie cookie = new GetCookie();
        String[] c = cookie.Cookie().split(";");
        System.out.println(c[0]);

        //读取文件夹中的json数据,获取需要的参数
        Params p = new Params(this.PATH);

        //获取api的数量
        int len = p.get_summary().size();

        ArrayList<String> params = p.full_params();  //用于存放拼接的params
        ArrayList<String> full_url = p.full_url(); //从json中取出的url
        ArrayList<String> cn_name = p.get_summary(); //从json中取出的中文名字

        ArrayList<String> cn_name_for_db = new ArrayList<>(); //从json中取出的中文名字('替换成#)

        ArrayList<String> res_time = new ArrayList<>(); //用于存放response time
        ArrayList<String> res_code = new ArrayList<>(); //用于存放response code

        //将中文名字中的'替换为#,方便写入数据库
        for (String CN_name : cn_name) {
            String sUmmary = CN_name.replace("'", "#"); //替换中文名字内的'
            cn_name_for_db.add(sUmmary);
        }

        for (int i = 0; i < len; i++) {
            ConcurrencyCore cc = new ConcurrencyCore(full_url.get(i), 100, params.get(i), c[0]);
            ConcurrentLinkedDeque temp = cc.concurrency();
            System.out.println(temp);
        }
    }

    public void tttt() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5, new WorkThreadFactory());
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        SendPostRequest re = new SendPostRequest(
                                "http://192.168.31.99:8088/API/Common/GetSysInfo", "APIVersion=999999999", "");
                        ArrayList t = new GetResponseAndCode(re.Post()).getRes();
                        System.out.println(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        fixedThreadPool.shutdown();
    }

    public static void main(String args[]) throws Exception {
        MainProcess m = new MainProcess("/Users/shishuaigang/Desktop/Auto_test/testjson");
        m.mainProcess();

    }
}
