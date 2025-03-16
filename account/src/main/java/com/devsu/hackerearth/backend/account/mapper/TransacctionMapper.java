package com.devsu.hackerearth.backend.account.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;

@Mapper
public interface TransacctionMapper {

    Transaction convertDtoToEntity(final TransactionDto transactionDto);

    TransactionDto convertEntityToDto(final Transaction transaction);

    List<TransactionDto> convertEntityListToDtoList(final List<Transaction> transactions);

}
