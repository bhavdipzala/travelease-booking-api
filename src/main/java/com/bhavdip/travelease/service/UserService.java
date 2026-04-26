package com.bhavdip.travelease.service;


import com.bhavdip.travelease.dto.user.UserRequestDTO;
import com.bhavdip.travelease.dto.user.UserResponseDTO;
import com.bhavdip.travelease.exception.BadRequestException;
import com.bhavdip.travelease.exception.ResourceNotFoundException;
import com.bhavdip.travelease.model.Role;
import com.bhavdip.travelease.model.User;
import com.bhavdip.travelease.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    private UserResponseDTO mapToUserResponseDTO(User user){
        UserResponseDTO dto = new UserResponseDTO();

        dto.setName(user.getName());
        dto.setBlocked(user.getBlocked());
        dto.setId(user.getId());
        dto.setRole(user.getRole().name());
        dto.setEmail(user.getEmail());

        return dto;
    }


    public UserResponseDTO createUser(UserRequestDTO userRequest){
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email already exists!");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);
        user.setBlocked(false);

        User savedUser = userRepository.save(user);

        return mapToUserResponseDTO(savedUser);
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this :: mapToUserResponseDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        return mapToUserResponseDTO(user);
    }

    public void deleteUser(Long userId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User not found!");
        }
        userRepository.deleteById(userId);
    }

    public UserResponseDTO blockUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (user.getBlocked()){
            throw new BadRequestException("User already blocked");
        }

        user.setBlocked(true);
        userRepository.save(user);

        return mapToUserResponseDTO(user);
    }

    public UserResponseDTO unblockUser (Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (!user.getBlocked()){
            throw new BadRequestException("User already unblocked");
        }

        user.setBlocked(false);
        userRepository.save(user);

        return mapToUserResponseDTO(user);
    }

    public UserResponseDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: "+ email));

        return mapToUserResponseDTO(user);
    }

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequest){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());

        User savedUser = userRepository.save(user);

        return mapToUserResponseDTO(savedUser);
    }


}