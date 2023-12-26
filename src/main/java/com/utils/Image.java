package com.utils;

import com.utils.impl.ImageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Image {

    byte[] partToBytes(HttpServletRequest request, String file) throws ServletException, IOException;

    String byteToString(byte[] imagedata);

    byte[] stringTobyte(String imagestring);

    static void main(String[] args) {
//        try {
//
//            File file = new File("C:/Users/CC/Pictures/Saved Pictures/1.png"); // 图片文件对象
//            BufferedImage image = ImageIO.read(file); // 加载图片文件并创建BufferedImage对象
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建字节数组输出流
//            ImageIO.write(image, "png", baos); // 将BufferedImage写入字节输出流。(同时指定文件类型)
//            byte[] imageData = baos.toByteArray(); // 字节数组
//            System.out.println(imageData);
//            String hexString = javax.xml.bind.DatatypeConverter.printHexBinary(imageData);
//            System.out.println(hexString);
//
//            //创建一个新的BufferedImage对象，并将字节数组作为像素数据填充到图像中。
//            BufferedImage imageb = createImageFromBytes(imageData);
//
//            ImageIO.write(imageb, "png", new File("C:/图片/奇遇原始人/桃夭2.png"));
//
//            JFrame frame = new JFrame();
//            JLabel label = new JLabel(new ImageIcon(image));
//            frame.getContentPane().add(label);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.pack();
//            frame.setVisible(true);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new ImageUtils().DrowImage();
    }
}
