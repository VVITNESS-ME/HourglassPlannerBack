package com.myweapon.hourglass.chatRoom.service;

import com.myweapon.hourglass.chatRoom.dto.ChatRoomRequest;
import com.myweapon.hourglass.chatRoom.dto.Room;
import com.myweapon.hourglass.chatRoom.dto.RoomResponse;
import com.myweapon.hourglass.chatRoom.entity.ChatRoom;
import com.myweapon.hourglass.chatRoom.repository.ChatRoomRepository;
import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public RoomResponse getAllRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<Room> rooms = chatRooms.stream()
                .map(ChatRoom::toRoom)
                .collect(Collectors.toList());
        return RoomResponse.builder()
                .rooms(rooms)
                .build();
    }

    public String createChatRoom(int limit, String name, Boolean isSecretRoom, String password) {
        ChatRoom chatRoom = ChatRoom.builder()
                .limit(limit)
                .joinedPeople(0)
                .name(name)
                .isSecretRoom(isSecretRoom)
                .password(password)
                .build();
        chatRoomRepository.save(chatRoom);
        return String.valueOf(chatRoom.getId());
    }

    public Room getChatRoomById(Long id) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            return chatRoom.toRoom();
        }
        throw new RestApiException(ErrorType.ROOM_IS_NOT_FOUND);
    }

    public String joinChatRoom(Long id, ChatRoomRequest chatRoomRequest) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            if(chatRoom.getIsSecretRoom()){
                if (chatRoom.getPassword().equals(chatRoomRequest.getPassword())) {
                    if(chatRoom.getJoinedPeople() < chatRoom.getLimit()){
                        return String.valueOf(id);
                    }else {
                        throw new RestApiException(ErrorType.ROOM_IS_FULL);
                    }
                } else {
                    throw new RestApiException(ErrorType.NOT_VALID_ROOM_PASSWORD);
                }
            } else {
                if(chatRoom.getJoinedPeople() < chatRoom.getLimit()){
                    return String.valueOf(id);
                }else {
                    throw new RestApiException(ErrorType.ROOM_IS_FULL);
                }
            }
        } else {
            throw new RestApiException(ErrorType.ROOM_IS_NOT_FOUND);
        }
    }

    public void changeParticipants(Long id, int participants) {
        chatRoomRepository.findById(id).ifPresent(chatRoom -> {
            if (participants == 0) {
                chatRoomRepository.delete(chatRoom);
            } else {
                chatRoom.changeJoinedPeople(participants);
                chatRoomRepository.save(chatRoom);
            }
        });
    }
}
