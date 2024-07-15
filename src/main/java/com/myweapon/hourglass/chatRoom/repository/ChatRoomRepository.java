package com.myweapon.hourglass.chatRoom.repository;

import com.myweapon.hourglass.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
