package com.hoon.chat.controller;

import com.hoon.chat.dto.ChatMessage;
import com.hoon.chat.repo.ChatRoomRepoImpl;
import com.hoon.chat.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;


@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;

    private final JwtTokenProvider jwtTokenProvider;

    private final ChatRoomRepoImpl chatRoomRepository;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token);

        //로그인 회원 정보로 대화명 설정
        message.setSender(userName);

        if(ChatMessage.MessageType.ENTER.equals(message.getType())){
            message.setSender(("[알림]"));
            message.setMessage(userName + "님이 입장하셨습니다.");
        }

        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }
}