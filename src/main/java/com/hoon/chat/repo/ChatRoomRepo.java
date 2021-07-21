package com.hoon.chat.repo;

import com.hoon.chat.dto.ChatRoom;

import java.util.List;

public interface ChatRoomRepo {

    public List<ChatRoom> findAllRoom();

    public ChatRoom findRoomById(String id);

    public ChatRoom createChatRoom(ChatRoom newRoom);
}
