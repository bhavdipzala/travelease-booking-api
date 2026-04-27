package com.bhavdip.travelease.controller;


import com.bhavdip.travelease.dto.user.UserRequestDTO;
import com.bhavdip.travelease.dto.user.UserResponseDTO;
import com.bhavdip.travelease.dto.user.UserUpdateRequestDto;
import com.bhavdip.travelease.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }



    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO user){
        return userService.createUser(user);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserById(@PathVariable Long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO getUserByEmail(@RequestParam String email){
        return userService.getUserByEmail(email);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
    }

    @PatchMapping("/{userId}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO blockUser(@PathVariable Long userId){
        return userService.blockUser(userId);
    }

    @PatchMapping("/{userId}/unblock")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO unblockUser(@PathVariable Long userId){
        return userService.unblockUser(userId);
    }

    @PatchMapping("/{userId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDTO updateUser(@PathVariable Long userId,
                                      @RequestBody @Valid UserUpdateRequestDto userRequest){
        return userService.updateUser(userId, userRequest);
    }

}
