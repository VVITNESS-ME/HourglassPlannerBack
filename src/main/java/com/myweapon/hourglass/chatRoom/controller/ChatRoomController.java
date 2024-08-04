package com.myweapon.hourglass.chatRoom.controller;

import com.myweapon.hourglass.chatRoom.dto.ChatRoomRequest;
import com.myweapon.hourglass.chatRoom.dto.ParticipantsRequest;
import com.myweapon.hourglass.chatRoom.dto.Room;
import com.myweapon.hourglass.chatRoom.dto.RoomResponse;
import com.myweapon.hourglass.chatRoom.entity.ChatRoom;
import com.myweapon.hourglass.chatRoom.service.ChatRoomService;
import com.myweapon.hourglass.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/together")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<RoomResponse>> getChatRooms() {
        return ResponseEntity.ok(ApiResponse.success(chatRoomService.getAllRooms()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        String chatRoomId = chatRoomService.createChatRoom(
                chatRoomRequest.getLimit(),
                chatRoomRequest.getTitle(),
                chatRoomRequest.getIsSecretRoom(),
                chatRoomRequest.getPassword());
        return ResponseEntity.ok(ApiResponse.success(chatRoomId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Room>> getChatRoomById(@PathVariable Long id) {
        Room room = chatRoomService.getChatRoomById(id);
        return ResponseEntity.ok(ApiResponse.success(room));
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<ApiResponse<String>> joinChatRoom(@PathVariable Long id, @RequestBody ChatRoomRequest chatRoomRequest) {
            String token = chatRoomService.joinChatRoom(id, chatRoomRequest);
            return ResponseEntity.ok(ApiResponse.success(token));
    }

    @PutMapping("/participants/{id}")
    public ResponseEntity<ApiResponse<Void>> leaveChatRoom(@PathVariable Long id, @RequestBody ParticipantsRequest participantsRequest) {
        chatRoomService.changeParticipants(id, participantsRequest.getCurrent());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
