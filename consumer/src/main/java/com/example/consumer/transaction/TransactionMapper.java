package com.example.consumer.transaction;

import com.example.consumer.client.ClientEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(uses = TransactionTypeMapper.class)
public interface TransactionMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "cost", expression = "java(countCost(dto))")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "bank", source = "dto.bank")
    @Mapping(target = "transactionType", source = "dto.transactionType")
    @Mapping(target = "quantity", source = "dto.quantity")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "createdAt", source = "dto.createdAt")
    TransactionEntity toEntity(final TransactionDto dto, final ClientEntity client);

    default BigDecimal countCost(
            final TransactionDto dto
    ) {
        return BigDecimal.valueOf(dto.getPrice())
                .multiply(BigDecimal.valueOf(dto.getQuantity()));
    }
}
