package com.controller.base;

import com.google.gson.GsonBuilder;
import com.utils.Email;
import com.utils.Image;
import com.utils.RandomUtils;
import com.utils.Video;
import com.vo.PageVo;
import com.vo.ResponseData;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public abstract class BaseController implements Image, Email, Video {

    /**
     * 重写Email接口方法
     */
    @Override
    public String getCap() {
        try {
            return RandomUtils.GetRandomNumber(6, 0, 9);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendEmail(String email, String content) {
        SimpleEmail mail = new SimpleEmail();
        //服务器地址
        mail.setHostName("smtp.163.com");
        //发件地址:邮箱，授权码（"xuyujin3306@163.com", "KSGVHEAKGPOBIZJN"）
        mail.setAuthentication("xuyujin3306@163.com", "KSGVHEAKGPOBIZJN");//QQ   smtp:ughmuiftketvbeac   pop3:blbktqbqgppdbbbg   shouquan:dpovvzugjwpfbcg
        //设置内容编码
        mail.setCharset("utf-8");

        try {
            mail.addTo(email);
            //设置发件邮箱
            mail.setFrom("xuyujin3306@163.com");
            //设置标题
            mail.setSubject("Your RainbowCloud launch code");
            mail.setMsg(content);
            mail.setSSL(true);
            mail.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 重写Image接口方法
     */

    /**
     * 将前端图片转换为字节流数组
     * @param request
     * @param file
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public byte[] partToBytes(HttpServletRequest request, String file) throws ServletException, IOException {
        byte[] buffer = new byte[1024];
        Part imagePart = request.getPart(file);
        try {
            InputStream inputStream = imagePart.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制数组转为字符串
     *
     * @param imagedata
     * @return
     */
    @Override
    public String byteToString(byte[] imagedata) {
        return Base64.getEncoder().encodeToString(imagedata);
    }

    /**
     * 将字符串转为二进制数组
     *
     * @param imagestring
     * @return
     */
    @Override
    public byte[] stringTobyte(String imagestring) {
        return Base64.getDecoder().decode(imagestring);
    }

    //重写Video接口方法

    /**
     * 处理前端视频
     * @param request
     * @param file
     * @return 视频的相对路径
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public String StoreVideo(HttpServletRequest request, String file) throws ServletException, IOException {
        Part filePart = request.getPart("video"); // 获取上传的文件
        String fileName = filePart.getSubmittedFileName(); // 获取文件名
        InputStream fileContent = filePart.getInputStream(); // 获取文件内容

        String filepath = "static/video" + "/" + fileName;
        String realpath = request.getServletContext().getRealPath(filepath);
        // 将文件保存到服务器的某个位置
        File targetFile = new File(realpath);
        try (OutputStream outStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
        return filepath;
    }

    @Override
    public byte[] VideoConvertByte(String videopath) throws IOException {
        Path path = Paths.get(videopath);
        return Files.readAllBytes(path);
    }

    public String ReadJson(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String temp;
        String json = "";
        while ((temp = reader.readLine()) != null) {
            json += temp;
        }
        return json;
    }
    /**
     * 成功响应的数据封装 - 默认
     *
     * @param data
     * @return
     */
    public ResponseData successJson(Object data) {
        return new ResponseData(200, "success", data);
    }

    /**
     * 重载成功响应数据封装方法
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public ResponseData successJson(Integer code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }

    /**
     * 失败响应的数据封装 - 默认
     *
     * @return
     */
    public ResponseData errorJson() {
        return new ResponseData(500, "error", null);
    }

    /**
     * 重载成功响应数据封装方法
     *
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public ResponseData errorJson(Integer code, String msg, Object data) {
        return new ResponseData(code, msg, data);
    }

    public String print(ResponseData responseData) throws IOException {
        // out.println(new Gson().toJson(responseData));
        return new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create()
                .toJson(responseData);
    }

    /**
     * 封装 PageVo 对象
     * @param count
     * @param data
     * @return
     */
    public PageVo pageVo(Long count, Object data) {
        return new PageVo(200,"success",data,count) ;
    }
}