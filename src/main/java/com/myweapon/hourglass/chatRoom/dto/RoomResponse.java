package com.myweapon.hourglass.chatRoom.dto;
import com.myweapon.hourglass.category.dto.UserCategoryGetResponse;
import com.myweapon.hourglass.category.dto.UserCategoryWithName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class RoomResponse {
    private List<Room> rooms;
}
