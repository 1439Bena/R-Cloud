package com.config;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @Author Cc
 * @Date 2023-12-22
 */
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
       //获取HttpSession对象
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //将httpSession保存
        if (httpSession != null){
            sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        }
    }
}
