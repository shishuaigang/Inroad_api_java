package com.inroad.apitest;

import com.inroad.apitest.scan.*;
import com.inroad.apitest.concurrency.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Inroad API测试主界面
 */

public class MainGUI {

    public void mainGUI() {
        JFrame frame = new JFrame("Inroad API测试");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setResizable(false); //窗口固定大小不能动
        frame.setLocationRelativeTo(null);

        Container container = frame.getContentPane();

        JPanel jp1 = new JPanel(); //装载label
        //JPanel jp2 = new JPanel(); //装载工之道图片
        JPanel jp3 = new JPanel(); //装载3个功能测试按钮

        //Font f = new Font("宋体",Font.BOLD,22);
        JButton btn1 = new JButton("API 扫描");
        JButton btn2 = new JButton("API 并发");
        JButton btn3 = new JButton("API 容错");
        //btn1.setFont(f);

        //API 遍历按钮
//        btn1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ScanGUI scan = new ScanGUI();
//                scan.scanGUI();
//            }
//        });
        btn1.addActionListener(e -> {
            ScanGUI scan = new ScanGUI();
            scan.scanGUI();
        });

        // API 并发按钮
        btn2.addActionListener(e -> {
            ConcurrencyGUI concurrency = new ConcurrencyGUI();
            concurrency.concurrencyGUI();
        });

        // API 容错按钮
        btn3.addActionListener(e -> JOptionPane.showMessageDialog(null, "功能正在开发中"));

        JLabel jLabel = new JLabel("Inroad API测试  Beta V1.0");
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);

        jp1.add(jLabel);
        jp3.add(btn1);
        jp3.add(btn2);
        jp3.add(btn3);

        container.add(jp1, BorderLayout.NORTH);
        container.add(jp3, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String args[]) {
        MainGUI mg = new MainGUI();
        mg.mainGUI();
    }
}
