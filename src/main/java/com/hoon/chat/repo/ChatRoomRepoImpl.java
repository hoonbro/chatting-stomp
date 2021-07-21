package com.hoon.chat.repo;

import com.hoon.chat.dto.ChatRoom;
import com.hoon.util.DBUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class ChatRoomRepoImpl {

    public List<ChatRoom> findAllRoom() throws SQLException {
        List<ChatRoom> chatRooms = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = DBUtil.getConnection();
            String sql = "select * from chatroom";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                ChatRoom room = new ChatRoom();
                room.setRoomId(rs.getString(2));
                room.setName(rs.getString(3));
                chatRooms.add(room);
            }
        } finally {
            DBUtil.close(rs,pstmt,con);
        }

        return chatRooms;
    }

    public ChatRoom findRoomById(String id) throws SQLException {
        ChatRoom chatRoom = new ChatRoom();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = DBUtil.getConnection();
            String sql = "select * from chatroom where roomId = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if(rs.next()){
                chatRoom.setRoomId(rs.getString(2));
                chatRoom.setName(rs.getString(3));
            }
        } finally {
            DBUtil.close(rs,pstmt,con);
        }

        return chatRoom;
    }

    public ChatRoom createChatRoom(ChatRoom newRoom) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try{
            con = DBUtil.getConnection();
            String sql = "insert into chatroom(roomId, name) values(?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, newRoom.getRoomId());
            pstmt.setString(2, newRoom.getName());
            pstmt.executeUpdate();
        } finally {
            DBUtil.close(pstmt,con);
        }

        return newRoom;
    }
}