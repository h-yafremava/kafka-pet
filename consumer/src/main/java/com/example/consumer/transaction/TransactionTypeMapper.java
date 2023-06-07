package com.example.consumer.transaction;

import org.mapstruct.Mapper;

@Mapper
public interface TransactionTypeMapper {

  default TransactionType toEnum(final String transactionTypeName) {
    return TransactionType.from(transactionTypeName);
  }
}
