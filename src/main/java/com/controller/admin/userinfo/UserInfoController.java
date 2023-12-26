package com.controller.admin.userinfo;

import com.bean.UserInfo;
import com.controller.base.BaseController;
import com.service.UserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-06
 */
@RestController
public class UserInfoController extends BaseController {
    @RequestMapping("/GetUser")
    protected String GetUserInfo(@RequestParam("page" ) int page,@RequestParam("limit") int limit){

        List<UserInfo> users=new UserInfoService().getUserInfo(null,page,limit);
        Long count=new UserInfoService().getUserCount(null);

        try {
            return print(pageVo(count,users));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
