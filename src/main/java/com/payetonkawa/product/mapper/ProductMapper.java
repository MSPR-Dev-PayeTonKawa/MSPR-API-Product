package com.payetonkawa.product.mapper;

import org.mapstruct.Mapper;

import com.payetonkawa.product.dto.PatchProductDto;
import com.payetonkawa.product.dto.PostProductDto;
import com.payetonkawa.product.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product fromPostDto(PostProductDto postProductDto);
    Product fromPatchDto(PatchProductDto patchProductDto);
    PostProductDto toPostDto(Product product);
    PatchProductDto toPatchDto(Product product);
}
