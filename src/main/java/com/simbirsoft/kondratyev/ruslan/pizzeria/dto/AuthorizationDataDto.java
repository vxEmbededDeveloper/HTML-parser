package com.simbirsoft.kondratyev.ruslan.pizzeria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationDataDto {
    private String login;
    private String password;
}