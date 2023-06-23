package com.example.consumer.client;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    @Mapping(target = "id", source = "clientId")
    ClientEntity toEntity(final ClientDto dto);
}
