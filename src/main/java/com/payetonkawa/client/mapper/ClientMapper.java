package com.payetonkawa.client.mapper;

import org.mapstruct.Mapper;

import com.payetonkawa.client.dto.PatchClientDto;
import com.payetonkawa.client.dto.PostClientDto;
import com.payetonkawa.client.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client fromPostDto(PostClientDto postClientDto);
    Client fromPatchDto(PatchClientDto patchClientDto);
}
