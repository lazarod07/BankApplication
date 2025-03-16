package com.devsu.hackerearth.backend.account.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.custom.TransactionAccountDto;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT"
    +" t.date as date,"
    +" a.number as number,"
    +" a.type as type,"
    +" a.initialAmount as initialAmount,"
    +" a.isActive as isActive,"
    +" t.type as transactionType,"
    +" t.amount as amount,"
    +" t.balance as balance"
    +" FROM Transaction t JOIN Account a ON t.accountId = a.id " +
    "WHERE a.clientId = :clientId " +
    "AND t.date BETWEEN :dateTransactionStart AND :dateTransactionEnd")
    List<TransactionAccountDto> getAllByAccountClientIdAndTransactionDateBetween(
        @Param("clientId") Long clientId,
        @Param("dateTransactionStart") Date dateTransactionStart,
        @Param("dateTransactionEnd") Date dateTransactionEnd);

}
