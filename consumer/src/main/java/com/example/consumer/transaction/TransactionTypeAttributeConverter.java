package com.example.consumer.transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeAttributeConverter
        implements AttributeConverter<TransactionType, Long> {

  @Override
  public Long convertToDatabaseColumn(final TransactionType attribute) {
    return attribute.getId();
  }

  @Override
  public TransactionType convertToEntityAttribute(final Long dbData) {
    return TransactionType.from(dbData);
  }
}
