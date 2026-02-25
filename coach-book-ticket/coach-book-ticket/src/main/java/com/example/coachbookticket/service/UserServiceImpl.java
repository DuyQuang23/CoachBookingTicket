package com.example.coachbookticket.service;

import com.example.coachbookticket.components.JwtTokenUtils;
import com.example.coachbookticket.dto.UserCreateDTO;
import com.example.coachbookticket.dto.UserDTO;
import com.example.coachbookticket.exception.ConflictException;
import com.example.coachbookticket.exception.DataNotFoundException;
import com.example.coachbookticket.exception.ResourceNotFoundException;
import com.example.coachbookticket.model.User;
import com.example.coachbookticket.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.coachbookticket.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repo;
    private final ModelMapper mapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;


    @Override
    public UserDTO create(UserCreateDTO dto) {
        if (repo.existsByPhone(dto.getPhone())) throw new ConflictException("PhoneNumber already taken");
        User u = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .role(User.Role.CUSTOMER)
                .status(true)
                .build();
        User saved = repo.save(u);
        return mapper.map(saved, UserDTO.class);
    }

    @Override
    public LoginResponse login(String phoneNumber, String passWord) throws Exception {

        Optional<User> optionalUser = repo.findByPhone(phoneNumber);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number or password");
        }
        User existingUser = optionalUser.get();
        if(!passwordEncoder.matches(passWord,existingUser.getPassword())) {
            throw new BadCredentialsException("Invalid phone number or password");
        }


        // Tạo token JWT
        String token = jwtTokenUtils.generateToken(existingUser);
        UserDTO userDTO = UserDTO.builder()
                .userId(existingUser.getUserId())
                .username(existingUser.getUsername())
                .fullName(existingUser.getFullName())
                .email(existingUser.getEmail())
                .phone(existingUser.getPhone())
                .role(existingUser.getRole().name()) // Giả sử role là Enum
                .build();

        // Trả về LoginResponse (token và thông tin user)
        return LoginResponse.builder()
                .token(token)
                .user(userDTO) // Đính kèm thông tin user
                .build();
    }

    @Override
    public UserDTO getById(Integer id) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public List<UserDTO> listAll() {
        return repo.findAll().stream().map(u -> mapper.map(u, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Integer id, UserCreateDTO dto) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getFullName() != null) u.setFullName(dto.getFullName());
        if (dto.getEmail() != null) u.setEmail(dto.getEmail());
        if (dto.getPhone() != null) u.setPhone(dto.getPhone());
        repo.save(u);
        return mapper.map(u, UserDTO.class);
    }

    @Override
    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("User", "id", id);
        repo.deleteById(id);
    }


}