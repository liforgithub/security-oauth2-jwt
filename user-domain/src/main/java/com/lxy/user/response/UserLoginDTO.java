package com.lxy.user.response;

import com.lxy.JWT;
import com.lxy.User;
import lombok.Data;

@Data
public class UserLoginDTO {
    private User user;
    private JWT jwt;
}
