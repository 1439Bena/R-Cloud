package com.utils.impl;

import com.utils.Image;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * @author Cc
 * @date 2023-10-2023
 */

public class ImageUtils implements Image {

    public void DrowImage() {
        String path = "C:/Users/CC/Pictures/Saved Pictures";
        String[] ima = {"/1.png", "/2.png", "/3.png", "/4.png", "/5.png", "/6.png", "/7.png", "/8.png", "/9.png", "/10.png"};
        for (String s : ima) {
            try {
                File file = new File("D:/图片" + s); // 图片文件对象
                BufferedImage image = ImageIO.read(file); // 加载图片文件并创建BufferedImage对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建字节数组输出流
                ImageIO.write(image, "png", baos); // 将BufferedImage写入字节输出流。(同时指定文件类型)
                byte[] imageData = baos.toByteArray(); // 字节数组

                JFrame frame = new JFrame();
                JLabel label = new JLabel(new ImageIcon(image));
                frame.getContentPane().add(label);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public byte[] partToBytes(HttpServletRequest request, String file) throws ServletException, IOException {
        return new byte[0];
    }

    @Override
    public String byteToString(byte[] imagedata) {
        return Base64.getEncoder().encodeToString(imagedata);
    }

    @Override
    public byte[] stringTobyte(String imagestring) {
        return Base64.getDecoder().decode(imagestring);
    }
}
