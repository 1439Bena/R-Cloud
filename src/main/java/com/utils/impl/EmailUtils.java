package com.utils.impl;


import com.utils.Email;

public class EmailUtils implements Email {

    public static void main(String[] args) {
        String Cap = new EmailUtils().getCap();
        System.out.println(Cap);
        new EmailUtils().sendEmail("892949622@qq.com", "亲爱的用户：\n\n\n您好！感谢您使用服务，您正在进行邮箱验证，本次请求的验证码为：" + Cap
                + "\n\n\n账号团队");
    }

    @Override
    public String getCap() {
        return null;
    }

    @Override
    public void sendEmail(String email, String content) {

    }
}
