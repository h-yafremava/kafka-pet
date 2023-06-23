package com.example.consumer.client;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "client")
@SuperBuilder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ClientEntity {

    @Id
    private Long id;

    private String email;

    private boolean blank;
}
