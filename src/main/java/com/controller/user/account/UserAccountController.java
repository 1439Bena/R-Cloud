package com.controller.user.account;

import com.bean.AccountInfo;
import com.bean.UserInfo;
import com.controller.base.BaseController;
import com.service.AccountInfoService;
import com.service.UserInfoService;
import com.utils.RandomUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author Cc
 * @Date 2023-12-28
 */
@RestController
public class UserAccountController extends BaseController {
    @RequestMapping("/GetCap")
    protected String GetCap(@RequestParam("email") String email, HttpSession session) throws IOException {
        String cap = getCap();
        String content = "亲爱的用户：\n\n\n您好！感谢您使用服务，您正在进行邮箱验证，本次请求的验证码为：" + cap + "\n\n\nRb-Cloud账号团队";
        sendEmail(email, content);
        session.setAttribute("cap", cap);
        return print(successJson(cap));
    }

    @RequestMapping("/SignUpAccount")
    protected String SignUpAccount(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("cap") String cap, @RequestParam("password") String password,@RequestParam("servercap") String servercap) throws Exception {
        AccountInfo accountInfo = new AccountInfo();


        if (cap == null || servercap == null || !cap.equals(servercap))
            return print(errorJson(401, "验证码错误", null));
        else {
            accountInfo.setUid("Rb_" + RandomUtils.GetRandomNumber(8, 0, 9));
            accountInfo.setUsername(username);
            accountInfo.setEmail(email);
            accountInfo.setPassword(password);
            accountInfo.setAstatus(0);
            accountInfo.setRegistrationtime(LocalDateTime.now());

            int row = new AccountInfoService().SignAccount(accountInfo);
            UserInfo userInfo = new UserInfo();
            userInfo.setUseruid(accountInfo.getUid());
            userInfo.setNickname("用户_" + RandomUtils.GetRandomNumber(8, 0, 9));
            new UserInfoService().addUser(userInfo);
            if (row > 0)
                return print(successJson(null));
            else
                return print(errorJson(500, "error", null));
        }
    }
    @RequestMapping("/SignInAccount")
    protected String SignIn(@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        AccountInfo accountInfo = new AccountInfo();

        accountInfo.setUsername(username);
        accountInfo.setPassword(password);

        AccountInfo account = new AccountInfoService().SignInAccount(accountInfo);
        if (account != null) {
            return print(successJson(account));
        } else
            return print(errorJson(401, "error", null));
    }
}
