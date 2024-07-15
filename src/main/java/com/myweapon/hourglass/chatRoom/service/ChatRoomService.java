package com.myweapon.hourglass.chatRoom.service;

import com.myweapon.hourglass.chatRoom.dto.ChatRoomRequest;
import com.myweapon.hourglass.chatRoom.entity.ChatRoom;
import com.myweapon.hourglass.chatRoom.repository.ChatRoomRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Page<ChatRoom> getChatRooms(int page, int size) {
        return chatRoomRepository.findAll(PageRequest.of(page, size));
    }

    public String createChatRoom(int limitPeople, String name, Boolean isSecretRoom, String passWord) {
        ChatRoom chatRoom = ChatRoom.builder()
                .limitPeople(limitPeople)
                .joinedPeople(0)
                .name(name)
                .isSecretRoom(isSecretRoom)
                .passWord(passWord)
                .build();
        chatRoomRepository.save(chatRoom);
        return String.valueOf(chatRoom.getId());
    }

    public Optional<ChatRoom> getChatRoomById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public String joinChatRoom(Long id, ChatRoomRequest chatRoomRequest) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            if(chatRoom.getIsSecretRoom()){
                if (chatRoom.getPassWord().equals(chatRoomRequest.getPassWord())) {
                    chatRoom.incrementJoinedPeople();
                    chatRoomRepository.save(chatRoom);

                    return String.valueOf(id);
                } else {
                    throw new IllegalArgumentException("Invalid password");
                }
            } else {
                chatRoom.incrementJoinedPeople();
                chatRoomRepository.save(chatRoom);

                return String.valueOf(id);
            }
        } else {
            throw new IllegalArgumentException("ChatRoom not found");
        }
    }

    public void leaveChatRoom(Long id) {
        chatRoomRepository.findById(id).ifPresent(chatRoom -> {
            chatRoom.decrementJoinedPeople();
            if (chatRoom.getJoinedPeople() == 0) {
                chatRoomRepository.delete(chatRoom);
            } else {
                chatRoomRepository.save(chatRoom);
            }
        });
    }
}
