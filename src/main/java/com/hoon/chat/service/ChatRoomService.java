package com.hoon.chat.service;

import com.hoon.chat.dto.ChatRoom;

import java.sql.SQLException;
import java.util.List;


public interface ChatRoomService {
    public List<ChatRoom> findAllRoom() throws SQLException;

    public ChatRoom findRoomById(String id) throws SQLException;

    public ChatRoom createChatRoom(String name) throws SQLException;
}
