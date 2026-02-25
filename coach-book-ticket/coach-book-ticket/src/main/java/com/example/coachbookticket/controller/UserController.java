package com.example.coachbookticket.controller;

import com.example.coachbookticket.dto.*;
import com.example.coachbookticket.response.LoginResponse;
import com.example.coachbookticket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService service) {
        this.userService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> create(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        try {
            // Lấy token và thông tin người dùng từ UserService
            LoginResponse loginResponse = userService.login(userLoginDTO.getPhone(), userLoginDTO.getPassword());

            // Trả về token và user trong response
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> list() {
        return ResponseEntity.ok(userService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


