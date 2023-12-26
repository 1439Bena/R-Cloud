package com.controller.admin;


import com.bean.AccountInfo;
import com.bean.UserInfo;
import com.controller.base.BaseController;
import com.google.gson.Gson;
import com.service.AccountInfoService;
import com.service.UserInfoService;
import com.utils.RandomUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-01
 */
@RestController
public class AccountController extends BaseController {
    @RequestMapping("/AddAccount")
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String json=ReadJson(request);

            AccountInfo accountInfo = new Gson().fromJson(json, AccountInfo.class);
            accountInfo.setUid("Rb_" + RandomUtils.GetRandomNumber(8, 0, 9));
            accountInfo.setAstatus(0);
            accountInfo.setRegistrationtime(LocalDateTime.now());
            int row = new AccountInfoService().SignAccount(accountInfo);
            UserInfo userInfo=new UserInfo();
            userInfo.setUseruid(accountInfo.getUid());
            userInfo.setNickname("用户_" + RandomUtils.GetRandomNumber(8,0,9));
            new UserInfoService().addUser(userInfo);
            if (row > 0)
               return print(successJson(null));
            else
               return print(errorJson(500, "error", null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @RequestMapping("/GetAccount")
    protected String GetAccount(@RequestParam(value = "username", required = false)  String username,@RequestParam(value = "phone", required = false) String phone, @RequestParam(value = "email", required = false) String email, @RequestParam("page") int page, @RequestParam("limit") int limit) throws ServletException, IOException {

        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setUsername(username);
        accountInfo.setAphone(phone);
        accountInfo.setEmail(email);

        List<AccountInfo> accounts=new AccountInfoService().getAccountInfo(accountInfo,page,limit);
        Long count=new AccountInfoService().getAccountInfoCount(accountInfo);

       return print(pageVo(count,accounts));
    }
    @RequestMapping("/UpdateAccount")
    protected String UpdateAccount(HttpServletRequest request) throws IOException {
        String json=ReadJson(request);

        AccountInfo accountInfo = new Gson().fromJson(json, AccountInfo.class);
        accountInfo.setAstatus(0);

        int row=new AccountInfoService().UpdateAccountInfo(accountInfo);
        if (row > 0)
           return print(successJson(null));
        else
           return print(errorJson(500, "error", null));
    }
    @RequestMapping("/UpdateStatu")
    protected String UpdateStatu(@RequestParam("uid") String uid, @RequestParam("statu") int statu) throws IOException {

        int row=new AccountInfoService().UpdateStatu(uid,statu);
        if (row>0)
           return print(successJson(null));
        else
           return print(errorJson());
    }
    @RequestMapping("/UploadAvatar")
    protected String UploadAvatar(@RequestParam("head") byte[] avatar) throws ServletException, IOException {
        String head=byteToString(avatar);
        return print(successJson(head));
    }
}
