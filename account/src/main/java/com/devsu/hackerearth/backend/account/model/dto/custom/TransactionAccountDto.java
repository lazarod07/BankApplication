package com.devsu.hackerearth.backend.account.model.dto.custom;

import java.util.Date;

public interface TransactionAccountDto {

    Date getDate();

    String getNumber();

    String getType();

    double getInitialAmount();
    
    boolean getIsActive();

    String getTransactionType();

    double getAmount();

    double getBalance();
}
