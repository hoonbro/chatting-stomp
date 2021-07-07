package com.hoon.chating.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketHandler extends TextWebSocketHandler {
	
	//웹소켓 세션을 담아둘 맵
	HashMap<String, WebSocketSession> map = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	//메세지 발송
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		for(String key : map.keySet()) {
			WebSocketSession ws = map.get(key);
			try {
				ws.sendMessage(new TextMessage(msg));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Override
	//client가 접속시 호출되는 메서드
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		map.put(session.getId(), session);
		logger.info(session + "클라이언트 접속");
	}
	
	@Override
	//client가 접속 해제시 호출되는 메서드
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		map.remove(session.getId());
		logger.info(session + "클라이언트 접속 해제"); 
	}
}
