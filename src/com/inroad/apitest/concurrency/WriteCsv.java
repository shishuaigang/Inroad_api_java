package com.inroad.apitest.concurrency;

import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class WriteCsv {
    private ArrayList<String> url;
    private ArrayList<String> cn_name;
    private ArrayList<String> min;
    private ArrayList<String> max;
    private ArrayList<String> average;
    private String folderName;

    public WriteCsv(ArrayList<String> OriginalUrl, ArrayList<String> cn_name, ArrayList<String> min,
                    ArrayList<String> max, ArrayList<String> average,
                    String folderName) {
        this.url = OriginalUrl;
        this.cn_name = cn_name;
        this.min = min;
        this.max = max;
        this.average = average;
        this.folderName = folderName; //时间戳命名的testresult文件夹
    }

    public void writeCsv() {
        try {
            File f = new File("/Users/shishuaigang/testResults/concurrency");
            if (!f.exists()) {
                f.mkdir();
                (new File("/Users/shishuaigang/testResults/concurrency/" + folderName)).mkdir();
            } else {
                (new File("/Users/shishuaigang/testResults/concurrency/" + folderName)).mkdir();
            }
            FileWriter writer = new FileWriter("/Users/shishuaigang/testResults/concurrency/" + folderName + "/result.csv");
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
