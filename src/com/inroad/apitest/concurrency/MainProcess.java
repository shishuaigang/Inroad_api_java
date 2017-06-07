package com.inroad.apitest.concurrency;

import com.inroad.apitest.common.GetCookie;
import com.inroad.apitest.common.Params;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

        //读取文件夹中的json数据,获取需要的参数
        Params p = new Params(this.PATH);

        //获取api的数量
        int len = p.get_summary().size();

        ArrayList<String> params = p.full_params();  //用于存放拼接的params
        ArrayList<String> url = p.url();
        ArrayList<String> full_url = p.full_url(); //从json中取出的url
        ArrayList<String> cn_name = p.get_summary(); //从json中取出的中文名字

        ArrayList<String> cn_name_for_db = new ArrayList<>(); //从json中取出的中文名字('替换成#)

        ArrayList<String> min_time = new ArrayList<>();
        ArrayList<String> max_time = new ArrayList<>();
        ArrayList<String> average_time = new ArrayList<>();

        //每个API轮流并发
        for (int i = 0; i < len; i++) {
            //将中文名字中的'替换为#,方便写入数据库
            String sUmmary = cn_name.get(i).replace("'", "#"); //替换中文名字内的'
            cn_name_for_db.add(sUmmary);
            //api并发
            ConcurrencyCore cc = new ConcurrencyCore(full_url.get(i), 100, params.get(i), c[0]);
            ConcurrentLinkedDeque temp = cc.concurrency();
            ArrayList<Float> aTime = new ArrayList<>();
            for (Object EX : temp) {
                aTime.add(Float.valueOf(EX.toString()));
            }
            float sum = 0;
            for (Float t : aTime) {
                sum += t;
            }
            average_time.add(new DecimalFormat("0.00").format(sum / aTime.size()));
            max_time.add(String.valueOf(Collections.max(aTime)));
            min_time.add(String.valueOf(Collections.min(aTime)));
        }
        WriteCsv wcsv = new WriteCsv(url, cn_name, min_time, max_time, average_time, foldername);
        wcsv.writeCsv();
        GenerateHtml GT = new GenerateHtml(begintime, url, cn_name, min_time, max_time, average_time, foldername);
        GT.createHtml();
    }

    public static void main(String args[]) throws Exception {
        MainProcess m = new MainProcess("/Users/shishuaigang/Desktop/Auto_test/testjson");
        m.mainProcess();
    }
}
