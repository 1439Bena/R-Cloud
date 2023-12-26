package com.controller.user;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class GoMainPageServlet
 */
public class GoMainPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoMainPageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin","http://127.0.0.1:5500");
        String username = request.getParameter("userName");
        String userpwd = request.getParameter("userPwd");
        String userchk = request.getParameter("userChk");
        if (userchk != null && userchk.equals("on")) {
            Cookie cookieName = new Cookie("userName", username);
            Cookie cookiePwd = new Cookie("userPwd", userpwd);

            cookieName.setMaxAge(60 * 60 * 24 * 7);
            cookiePwd.setMaxAge(60 * 60 * 24 *7);
            response.addCookie(cookieName);
            response.addCookie(cookiePwd);
        }else { // 没有选中
            // 如果之前记住了，要删掉之前的cookie
            Cookie[] cookies = request.getCookies();
            Cookie NameCookie = null;
            Cookie PwdCookie = null;
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        NameCookie = cookie;
                    }
                    if (cookie.getName().equals("userPwd")) {
                        PwdCookie = cookie;
                    }
                }
                if (NameCookie != null && PwdCookie != null) {
                    // 删除cookie
                    NameCookie.setMaxAge(0);
                    PwdCookie.setMaxAge(0);
                    // 发送
                    response.addCookie(NameCookie);
                    response.addCookie(PwdCookie);
                }
            }

        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
