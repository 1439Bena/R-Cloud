package com.controller.user;

import com.service.ThumbsupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Cc
 * @Date 2024-01-06
 */
@RestController
public class ThumbsupController {
    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public ResponseEntity<String> likePost(@RequestParam("pid") String pid, @RequestParam("uid") String uid) {
        try {
            if (new ThumbsupService().checkLiked(pid, uid)) {
                return ResponseEntity.ok("Already liked");
            }

            if (new ThumbsupService().addLike(pid, uid)) {
                return ResponseEntity.ok("Like success");
            } else {
                return ResponseEntity.ok("Like failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/unlike", method = RequestMethod.POST)
    public ResponseEntity<String> unlikePost(@RequestParam("pid") String pid, @RequestParam("uid") String uid) {
        try {
            if (!new ThumbsupService().checkLiked(pid, uid)) {
                return ResponseEntity.ok("Not liked yet");
            }

            if (new ThumbsupService().cancelLike(pid, uid)) {
                return ResponseEntity.ok("Cancel like success");
            } else {
                return ResponseEntity.ok("Cancel like failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
