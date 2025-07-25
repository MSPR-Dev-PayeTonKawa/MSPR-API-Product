package com.payetonkawa.product.dto;

import java.sql.Date;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchProductDto {
    private Integer idProduct;

    private String libelle;

    private Integer stock;

    private Integer unitPrice;
}
