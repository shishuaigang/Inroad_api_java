package com.inroad.apitest.concurrency;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class WriteCsv {
    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> min;
    private ArrayList<String> max;
    private ArrayList<String> average;
    private String folderName;

    WriteCsv(ArrayList<String> OriginalUrl, ArrayList<String> cn_name, ArrayList<String> min,
                    ArrayList<String> max, ArrayList<String> average,
                    String folderName) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.min = min;
        this.max = max;
        this.average = average;
        this.folderName = folderName; //时间戳命名的testresult文件夹
    }

    void writeCsv() {

        String path;
        String _path;
        String _path_;
        if (System.getProperty("os.name").contains("Windows")) {
            path = "C:\\testResults\\Inroad";
            _path = "C:\\testResults\\Inroad\\concurrency";
            _path_ = "C:\\testResults\\Inroad\\concurrency\\";
        } else {
            path = "/Users/shishuaigang/testResults/Inroad";
            _path = "/Users/shishuaigang/testResults/Inroad/concurrency";
            _path_ = "/Users/shishuaigang/testResults/Inroad/concurrency/";
        }

        //创建文件夹，一层一层创建
        try {
            File f1 = new File(path);
            File f2 = new File(_path);
            File f3 = new File(_path_ + folderName);
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

            FileWriter writer;
            if (System.getProperty("os.name").contains("Windows")) {
                writer = new FileWriter(_path_ + folderName + "\\result.csv");
            } else {
                writer = new FileWriter(_path_ + folderName + "/result.csv");
            }

            CsvWriter csvWriter = new CsvWriter(writer, ',');
            String[] contents = {"API_URL", "API_Chinese_Name", "Min_Time", "Max_Time", "Average_Time"};//一行的方式写入
            csvWriter.writeRecord(contents);
            for (int i = 0; i < url.size(); i++) {
                csvWriter.writeRecord(
                        new String[]{url.get(i), cn_name.get(i), min.get(i), max.get(i), average.get(i)}
                );
            }
                csvWriter.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
