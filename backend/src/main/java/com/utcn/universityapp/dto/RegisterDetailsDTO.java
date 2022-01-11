package com.utcn.universityapp.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDetailsDTO {

    private RegisterAccountDTO account;
    private RegisterUserDTO user;

}
