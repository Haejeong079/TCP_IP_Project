package com.example.Pratice.service;



import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@ServerEndpoint("/chatt")
public class WebSocketChat {
    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen //클라이언트가 접속할 때 발생하는 이벤트
    public void onOpen(Session s){
        log.info("open session : "+ s.toString());
        if (!clients.contains(s)){
            clients.add(s);
            log.info("session open : "+ s);
        }else{
            log.info("이미 연결된 session 입니다 !");
        }
    }

    @OnMessage //메세지가 수신되었을때
    public void onMessage(String msg, Session session)throws Exception{
        log.info("receive message : " + msg);
        for(Session s : clients){
            log.info("send data :" + msg);
            s.getBasicRemote().sendText(msg);
        }

    }



    @OnClose //클라이언트가 브라우저를 끄거나 다른 경로로 이동할때
    public void onClose(Session s){
        log.info("session close : " + s);
        clients.remove(s);

    }

}
