package com.controller.user.post;

import com.bean.AccountInfo;
import com.bean.Post;
import com.bean.PostInfo;
import com.controller.base.BaseController;
import com.service.PostInfoService;
import com.service.PostService;
import com.utils.RandomUtils;
import com.vo.PageVo;
import com.vo.VideoRes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author Cc
 * @Date 2023-12-25
 */
@RestController
public class PostController extends BaseController {
    @RequestMapping("/GetPost")
    protected String GetPost(@RequestParam(value = "key", required = false) String key, @RequestParam("page") int page) throws IOException {

        List<Post> posts = new PostService().GetPost(page, 5);
        Long count = new PostService().GetPostCount();

        return print(new PageVo(200, "success", posts, count));
    }

    @RequestMapping("/GetLikeAndComment")
    protected String GetLikeAndComment(@RequestParam("pid") String pid) throws IOException {

        PostInfo postInfo = new PostInfoService().selectLikesAndCommentsCount(pid);

        return print(successJson(postInfo));
    }

    @RequestMapping("/ViewPost")
    protected String ViewPost(@RequestParam("pid") String pid) throws IOException {

        Post post = new PostService().PostInfo(pid);

        return print(successJson(post));
    }

    @RequestMapping("UploadVideo")
    protected String UploadVideo(HttpServletRequest request) throws ServletException, IOException {
        String url = StoreVideo(request, "wangeditor-uploaded-video");

        return pring(new VideoRes(0, url));
    }

    @RequestMapping("/PubPost")
    protected String PubPost(HttpServletRequest request) throws Exception {
        String pcontent = request.getParameter("pcontent");
        String publisheruid = request.getParameter("publisheruid");
        String image = request.getParameter("image");
        String videourl = request.getParameter("videourl");

        Post post = new Post();
        post.setPid("Post_" + RandomUtils.GetRandomNumber(8, 0, 9));
        post.setPcontent(pcontent);
        if (image != null && !"undefined".equals(image))
            post.setPimage(image);
        if (videourl != null && !"undefined".equals(videourl))
            post.setPvideo(videourl);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUid(publisheruid);
        post.setPublishaccountInfo(accountInfo);
        post.setPtime(new Date());
        post.setPstatus("正常");

        int row = new PostService().PubPost(post);
        if (row > 0)
            return print(successJson(null));
        else
            return print(errorJson());
    }
}
