package com.service;

import com.dao.ThumbsupDao;
import com.dao.impl.ThumbsupDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ThumbsupService {
    final ThumbsupDao dao = new ThumbsupDaoImpl();
    public boolean checkLiked(String pid, String uid) {
        return dao.checkLiked(pid, uid);
    }

    public boolean addLike(String pid, String uid) {
        return dao.addLike(pid, uid);
    }

    public boolean cancelLike(String pid, String uid) {
        return dao.cancelLike(pid, uid);
    }
}