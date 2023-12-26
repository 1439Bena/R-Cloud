package com.controller.user.post;

import com.bean.Post;
import com.bean.PostInfo;
import com.controller.base.BaseController;
import com.service.PostInfoService;
import com.service.PostService;
import com.vo.PageVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-25
 */
@RestController
public class PostController extends BaseController {
    @RequestMapping("/GetPost")
    protected String GetPost(@RequestParam(value = "key",required = false) String key, @RequestParam("page") int page) throws IOException {

        List<Post> posts = new PostService().GetPost(key,page,5);
        Long count=new PostService().GetPostCount(key);

        return print(new PageVo(200,"success",posts,count));
    }
    @RequestMapping("/GetLikeAndComment")
    protected String GetLikeAndComment(@RequestParam("pid") String pid) throws IOException {

        PostInfo postInfo=new PostInfoService().selectLikesAndCommentsCount(pid);

        return print(successJson(postInfo));
    }
    @RequestMapping("/ViewPost")
    protected String ViewPost(@RequestParam("pid") String pid) throws IOException {

        Post post = new PostService().PostInfo(pid);

        return print(successJson(post));
    }
}
