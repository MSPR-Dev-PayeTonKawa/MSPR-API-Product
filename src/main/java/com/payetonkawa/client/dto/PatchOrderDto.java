package com.payetonkawa.order.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchOrderDto {
    private Integer idOrder;
    
    private String lastname;

    private String firstname;

    private String address;

    private String email;

    private String phoneNumber;

    private Date dateOfCreation;
}
