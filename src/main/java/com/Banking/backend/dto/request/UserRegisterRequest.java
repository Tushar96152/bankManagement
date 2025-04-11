package com.Banking.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String dob;
    private String gender;

}
