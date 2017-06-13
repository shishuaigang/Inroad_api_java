package com.inroad.apitest.scan;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shishuaigang on 2017/5/2.
 * 写入csv,一行一行的写入
 */

class WriteCsv {

    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> res_time;
    private ArrayList<String> res_code;
    private ArrayList<String> res_status;
    private ArrayList<String> res_error_message;
    private String t_snap;

    WriteCsv(ArrayList<String> OriginalUrl, ArrayList<String> cn_name, ArrayList<String> res_time,
             ArrayList<String> res_code, ArrayList<String> res_status,
             ArrayList<String> res_error_message, String t) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.res_time = res_time;
        this.res_code = res_code;
        this.res_status = res_status;
        this.res_error_message = res_error_message;
        this.t_snap = t; //时间戳命名的testresult文件夹
    }

    void writeCsv() {
        //windows 类unix文件夹命令有别
        String path;
        String _path;
        String _path_;
        if (System.getProperty("os.name").contains("Windows")) {
            path = "C:\\testResults\\Inroad";
            _path = "C:\\testResults\\Inroad\\scan";
            _path_ = "C:\\testResults\\Inroad\\scan\\";
        } else {
            path = "/Users/shishuaigang/testResults/Inroad";
            _path = "/Users/shishuaigang/testResults/Inroad/scan";
            _path_ = "/Users/shishuaigang/testResults/Inroad/scan/";
        }

        try {
            File f1 = new File(path);
            File f2 = new File(_path);
            File f3 = new File(_path_ + t_snap);
            if(!f1.exists()){
                f1.mkdir();
                f2.mkdir();
                f3.mkdir();
            }else if(!f2.exists()){
                f2.mkdir();
                f3.mkdir();
            }else {
                f3.mkdir();
            }

            //windows 类unix系统创建文件命令有别
            FileWriter writer;
            if (System.getProperty("os.name").contains("Windows")) {
                writer = new FileWriter(_path_ + t_snap + "\\result.csv");
            } else {
                writer = new FileWriter(_path_ + t_snap + "/result.csv");
            }

            CsvWriter csvWriter = new CsvWriter(writer, ',');
            String[] contents = {"API_URL", "API_Chinese_Name", "Response_Time", "Response_Code", "Status", "Error_Message"};//一行的方式写入
            csvWriter.writeRecord(contents);
            for (int i = 0; i < this.url.size(); i++) {
                csvWriter.writeRecord(
                        new String[]{url.get(i), cn_name.get(i), res_time.get(i), res_code.get(i), res_status.get(i),
                                res_error_message.get(i)});
            }
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
