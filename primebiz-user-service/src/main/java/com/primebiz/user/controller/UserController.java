package com.primebiz.user.controller;

import com.primebiz.user.dto.AddressDTO;
import com.primebiz.user.dto.UserProfileDTO;
import com.primebiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId, @RequestBody UserProfileDTO profile) {
        userService.updateProfile(userId, profile);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @PostMapping("/{userId}/addresses")
    public ResponseEntity<?> addAddress(@PathVariable Long userId, @RequestBody AddressDTO address) {
        userService.addAddress(userId, address);
        return ResponseEntity.ok("Address added successfully");
    }
}
