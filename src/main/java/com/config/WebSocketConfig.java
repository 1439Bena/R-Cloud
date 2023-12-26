package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author Cc
 * @Date 2023-12-22
 */
@Configuration
public class WebSocketConfig {
    @Bean
    //注入ServerEndPointExporter 自动注册使用@ServerEndPoint注解
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
