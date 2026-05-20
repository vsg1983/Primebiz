package com.primebiz.user.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String preferredLanguage;
    private List<AddressDTO> addresses;
}
