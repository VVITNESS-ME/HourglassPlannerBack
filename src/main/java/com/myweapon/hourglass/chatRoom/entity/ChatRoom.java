package com.myweapon.hourglass.chatRoom.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="chat_room")
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int limitPeople;
    private int joinedPeople;
    private String Name;
    private Boolean isSecretRoom;
    private String passWord;

    @Builder
    public ChatRoom(int limitPeople, int joinedPeople, String name, Boolean isSecretRoom, String passWord) {
        this.limitPeople = limitPeople;
        this.joinedPeople = joinedPeople;
        this.Name = name;
        this.isSecretRoom = isSecretRoom;
        this.passWord = passWord;
    }

    public void incrementJoinedPeople() {
        this.joinedPeople += 1;
    }

    public void decrementJoinedPeople() {
        if (this.joinedPeople > 0) {
            this.joinedPeople -= 1;
        }
    }
}