package com.payetonkawa.product.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_product")
    private Integer idProduct;

    @Column(name="libelle")
    private String libelle;

    @Column(name="stock")
    private Integer stock;

    @Column(name="unit_price")
    private String unitPrice;
}
