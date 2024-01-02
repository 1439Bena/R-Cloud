package com.controller.admin;


import com.bean.AccountInfo;
import com.bean.UserInfo;
import com.controller.admin.pojo.AccountInfoData;
import com.controller.base.BaseController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.service.AccountInfoService;
import com.service.UserInfoService;
import com.utils.RandomUtils;
import org.springframework.web.bind.annotation.*;

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
    protected String AddAccount(@RequestBody AccountInfoData data){
        try {

            AccountInfo accountInfo = new AccountInfo(data.getUid(),data.getUsername(),data.getPassword(),data.getAvatar(),data.getEmail(),data.getAphone(),null,0,null);

            accountInfo.setUid("Rb_" + RandomUtils.GetRandomNumber(8, 0, 9));
            accountInfo.setAstatus(0);
            accountInfo.setRegistrationtime(LocalDateTime.now());
            int row = new AccountInfoService().AddAccount(accountInfo);
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
    protected String GetAccount(@RequestParam(value = "likeusername", required = false)  String username,@RequestParam(value = "likephone", required = false) String phone, @RequestParam(value = "likeemail", required = false) String email, @RequestParam("page") int page, @RequestParam("limit") int limit) throws ServletException, IOException {

        AccountInfo accountInfo=new AccountInfo();
        accountInfo.setUsername(username);
        accountInfo.setAphone(phone);
        accountInfo.setEmail(email);

        List<AccountInfo> accounts=new AccountInfoService().getAccountInfo(accountInfo,page,limit);
        Long count=new AccountInfoService().getAccountInfoCount(accountInfo);

       return print(pageVo(count,accounts));
    }
    @PostMapping("/UpdateAccount")
    protected String UpdateAccount(@RequestBody AccountInfoData data) throws IOException {

        AccountInfo accountInfo = new AccountInfo(data.getUid(),data.getUsername(),data.getPassword(),data.getAvatar(),data.getEmail(),data.getAphone(),null,0,null);

        int row=new AccountInfoService().UpdateAccountInfo(accountInfo);
        if (row > 0)
           return print(successJson(null));
        else
           return print(errorJson());
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
    protected String UploadAvatar(HttpServletRequest request) throws IOException, ServletException {
        byte[] avatar = partToBytes(request,"head");
        String head = byteToString(avatar);
        return print(successJson(head));
    }
}
