package com.utils.impl;

import com.utils.Video;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class VideoUtils implements Video {

    @Override
    public String StoreVideo(HttpServletRequest request, String file) throws ServletException, IOException {
        return null;
    }

    @Override
    public byte[] VideoConvertByte(String videopath) throws IOException {
        return new byte[0];
    }
}
