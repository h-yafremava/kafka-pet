package com.example.consumer.transaction;

import com.example.consumer.exception.InvalidArgumentException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    INCOME(1L, "INCOME"),
    OUTCOME(2L, "OUTCOME");

    final Long id;
    final String name;

    static final Map<Long, TransactionType> CACHE_BY_ID =
            stream(values()).collect(toUnmodifiableMap(TransactionType::getId, identity()));

    static final Map<String, TransactionType> CACHE_BY_NAME =
            stream(values()).collect(toUnmodifiableMap(TransactionType::getName, identity()));

    public static TransactionType from(final String name) {
        return ofNullable(CACHE_BY_NAME.get(name)).orElseThrow(() ->
                new InvalidArgumentException("Unrecognized [%s] with name: [%s]"
                        .formatted(TransactionType.class.getSimpleName(), name)));
    }

    public static TransactionType from(final Long id) {
        return ofNullable(CACHE_BY_ID.get(id)).orElseThrow(() ->
                new InvalidArgumentException("Unrecognized [%s] with id: [%s]".
                        formatted(TransactionType.class.getSimpleName(), id)));
    }
}
