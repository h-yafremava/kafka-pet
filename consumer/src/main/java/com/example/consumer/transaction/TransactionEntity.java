package com.example.consumer.transaction;

import com.example.consumer.client.ClientEntity;
//import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Table(name = "transaction")
@SuperBuilder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    private String bank;

    @Column(name = "transaction_type_id")
    private TransactionType transactionType;

    private Integer quantity;

    private Double price;

    private BigDecimal cost;

    private LocalDateTime createdAt;
}
