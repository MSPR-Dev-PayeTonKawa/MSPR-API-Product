package com.payetonkawa.client.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostClientDto {
    private String lastname;

    private String firstname;

    private String address;

    private String email;

    private String phoneNumber;

    private Date dateOfCreation;
}
