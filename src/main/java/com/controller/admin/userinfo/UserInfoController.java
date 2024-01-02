package com.controller.admin.userinfo;

import com.bean.UserInfo;
import com.controller.base.BaseController;
import com.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-06
 */
@RestController
public class UserInfoController extends BaseController {
    @RequestMapping("/GetUser")
    protected String GetUserInfo(@RequestParam(value = "nickname", required = false) String nickname, @RequestParam(value = "gender", required = false) String gender, @RequestParam("page") int page, @RequestParam("limit") int limit) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setGender(gender);

        List<UserInfo> users = new UserInfoService().getUserInfo(userInfo, page, limit);
        Long count = new UserInfoService().getUserCount(userInfo);

        try {
            return print(pageVo(count, users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/UpdateUserInfo")
    protected String UpdateUserInfo(@RequestBody UserInfo userInfo) throws IOException {
        int row = new UserInfoService().UpdateUser(userInfo);

        if (row > 0)
            return print(successJson(null));
        else
            return print(errorJson());
    }
}
