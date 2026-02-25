package com.example.coachbookticket.service;

import com.example.coachbookticket.dto.UserCreateDTO;
import com.example.coachbookticket.dto.UserDTO;
import com.example.coachbookticket.response.LoginResponse;

import java.util.List;

public interface UserService {
    UserDTO create(UserCreateDTO dto);
    UserDTO getById(Integer id);
    List<UserDTO> listAll();
    UserDTO update(Integer id, UserCreateDTO dto);
    void delete(Integer id);
    LoginResponse login(String phoneNumber, String password) throws Exception;
}
