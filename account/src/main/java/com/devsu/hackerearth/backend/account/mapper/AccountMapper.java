package com.devsu.hackerearth.backend.account.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;

@Mapper
public interface AccountMapper {

    Account convertDtoToEntity(final AccountDto accountDto);

    AccountDto convertEntityToDto(final Account account);

    List<AccountDto> convertEntityListToDtoList(final List<Account> accounts);

}
