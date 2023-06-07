package com.example.consumer.transaction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t JOIN FETCH t.client")
    TransactionEntity findByClientId(final Long clientId);

    boolean existsByClientId(final Long clientId);

}

