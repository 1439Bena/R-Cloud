package com.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Video {
    String StoreVideo(HttpServletRequest request, String file) throws ServletException, IOException;

    byte[] VideoConvertByte(String videopath) throws IOException;
}
