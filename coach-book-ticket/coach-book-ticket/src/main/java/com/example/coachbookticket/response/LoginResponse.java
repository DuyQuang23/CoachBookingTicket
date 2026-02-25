package com.example.coachbookticket.response;

import com.example.coachbookticket.dto.UserDTO;
import com.example.coachbookticket.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class LoginResponse {
    @JsonProperty("token")
    private String token;

    @JsonProperty("user")
    private UserDTO user; // Thông tin người dùng
}
