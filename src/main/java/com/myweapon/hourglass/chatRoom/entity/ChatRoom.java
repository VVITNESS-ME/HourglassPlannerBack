package com.myweapon.hourglass.chatRoom.entity;

import com.myweapon.hourglass.chatRoom.dto.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="chat_room")
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int limit;
    private int joinedPeople;
    private String Name;
    private Boolean isSecretRoom;
    private String password;

    @Builder
    public ChatRoom(int limit, int joinedPeople, String name, Boolean isSecretRoom, String password) {
        this.limit = limit;
        this.joinedPeople = joinedPeople;
        this.Name = Objects.requireNonNull(name, "Name cannot be null");
        this.isSecretRoom = Objects.requireNonNull(isSecretRoom, "isSecretRoom cannot be null");
        if (isSecretRoom){
            this.password = Objects.requireNonNull(password, "Password cannot be null");
        }
    }

    public void changeJoinedPeople(int participants) {
        if (this.joinedPeople > 0) {
            this.joinedPeople = participants;
        }
    }

    public Room toRoom() {
        return Room.builder()
                .roomId(this.id)
                .title(this.Name)
                .isSecretRoom(this.isSecretRoom)
                .limit(this.limit)
                .participants(this.joinedPeople)
                .build();
    }
}
