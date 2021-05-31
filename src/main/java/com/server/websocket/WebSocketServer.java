package com.server.websocket;

import com.server.utils.GetSpringBean;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/webSocket")
public class WebSocketServer {

    private Session session;

    private WebSocketUtils webSocketUtils = (WebSocketUtils) GetSpringBean.getBean("webSocketUtils");


    @OnOpen
    public void open(Session session){
        System.out.println(session.getId());
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
    }

    @OnError
    public void error(Session session, Throwable error){
        webSocketUtils.remove(session.getId());
    }

}
