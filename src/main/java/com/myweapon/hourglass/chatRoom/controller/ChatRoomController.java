package com.myweapon.hourglass.chatRoom.controller;

import com.myweapon.hourglass.chatRoom.dto.ChatRoomRequest;
import com.myweapon.hourglass.chatRoom.entity.ChatRoom;
import com.myweapon.hourglass.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("")
    public ResponseEntity<Page<ChatRoom>> getChatRooms(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(chatRoomService.getChatRooms(page, size));
    }

    @PostMapping("")
    public ResponseEntity<String> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest) {
        String chatRoom = chatRoomService.createChatRoom(
                chatRoomRequest.getLimitPeople(),
                chatRoomRequest.getName(),
                chatRoomRequest.getIsSecretRoom(),
                chatRoomRequest.getPassWord());
        return ResponseEntity.ok(chatRoom);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoom> getChatRoomById(@PathVariable Long id) {
        Optional<ChatRoom> chatRoom = chatRoomService.getChatRoomById(id);
        return chatRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<String> joinChatRoom(@PathVariable Long id, @RequestBody ChatRoomRequest chatRoomRequest) {
        try {
            String token = chatRoomService.joinChatRoom(id, chatRoomRequest);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/leave/{id}")
    public ResponseEntity<Void> leaveChatRoom(@PathVariable Long id) {
        chatRoomService.leaveChatRoom(id);
        return ResponseEntity.ok().build();
    }
}