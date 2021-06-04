package com.server.websocket;

import com.server.utils.GetSpringBean;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@Component
@ServerEndpoint(value = "/webSocket")
public class WebSocketServer {


    /**
     * Spingboot + webSocket 整合nginx配置文件
     *
     * server {
     *         listen     10010;
     *         server_name  www.linuxa-nginx.com;
     *         location / {
     *          proxy_pass http://192.168.56.113:8080;
     *        	proxy_redirect off;
     * 	    	proxy_http_version 1.1;
     * 	    	proxy_set_header Upgrade $http_upgrade;
     * 	    	proxy_set_header Connection "upgrade";
     *         	proxy_set_header Host $host:$server_port;
     *         	proxy_set_header X-Real-IP $remote_addr;
     *         	proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     *         }
     *        }
     */

    private Session session;

    private WebSocketUtils webSocketUtils = (WebSocketUtils) GetSpringBean.getBean("webSocketUtils");


    @OnOpen
    public void open(Session session){
        this.session = session;
        webSocketUtils.add(session.getId(), session);
    }

    @OnClose
    public void close(Session session){
        webSocketUtils.remove(session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println(message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void error(Session session, Throwable error){
        webSocketUtils.remove(session.getId());
    }

}
