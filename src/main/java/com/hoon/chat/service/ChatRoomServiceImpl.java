package com.hoon.chat.service;

import com.hoon.chat.dto.ChatRoom;
import com.hoon.chat.repo.ChatRoomRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

    @Autowired
    private ChatRoomRepoImpl chatRoomRepository;

    @Override
    public List<ChatRoom> findAllRoom() throws SQLException {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    @Override
    public ChatRoom findRoomById(String roomId) throws SQLException {
        return chatRoomRepository.findRoomById(roomId);
    }

    @Override
    public ChatRoom createChatRoom(String name) throws SQLException {
        ChatRoom newRoom = ChatRoom.create(name);
        return chatRoomRepository.createChatRoom(newRoom);
    }
}
