package com.primebiz.user.service;

import com.primebiz.user.dto.AddressDTO;
import com.primebiz.user.dto.UserProfileDTO;
import com.primebiz.user.model.Address;
import com.primebiz.user.model.UserProfile;
import com.primebiz.user.repository.AddressRepository;
import com.primebiz.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserProfileRepository profileRepository;
    private final AddressRepository addressRepository;

    public UserProfileDTO getProfile(Long userId) {
        UserProfile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        List<AddressDTO> addresses = addressRepository.findByUserId(userId).stream()
                .map(a -> AddressDTO.builder()
                        .id(a.getId())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .state(a.getState())
                        .pincode(a.getPincode())
                        .isDefault(a.isDefault())
                        .build())
                .collect(Collectors.toList());

        return UserProfileDTO.builder()
                .userId(profile.getUserId())
                .fullName(profile.getFullName())
                .phoneNumber(profile.getPhoneNumber())
                .preferredLanguage(profile.getPreferredLanguage())
                .addresses(addresses)
                .build();
    }

    @Transactional
    public UserProfile updateProfile(Long userId, UserProfileDTO dto) {
        UserProfile profile = profileRepository.findById(userId)
                .orElseGet(() -> UserProfile.builder().userId(userId).build());

        profile.setFullName(dto.getFullName());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setPreferredLanguage(dto.getPreferredLanguage());

        return profileRepository.save(profile);
    }

    @Transactional
    public Address addAddress(Long userId, AddressDTO dto) {
        if (dto.isDefault()) {
            addressRepository.findByUserId(userId).forEach(a -> a.setDefault(false));
        }

        Address address = Address.builder()
                .userId(userId)
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .isDefault(dto.isDefault())
                .build();

        return addressRepository.save(address);
    }

    public Address __getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new RuntimeException("No default address found"));
    }
}
