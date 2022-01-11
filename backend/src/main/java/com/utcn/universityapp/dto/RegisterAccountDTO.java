package com.utcn.universityapp.dto;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAccountDTO {

    private String username;
    private String password;
    private String email;

}
