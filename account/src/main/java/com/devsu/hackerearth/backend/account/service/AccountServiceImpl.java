package com.devsu.hackerearth.backend.account.service;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.mapper.AccountMapper;
import com.devsu.hackerearth.backend.account.model.Account;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.repository.AccountRepository;
import com.devsu.hackerearth.backend.account.service.external.ClientExternalService;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ClientExternalService clientExternalService;

    public AccountServiceImpl(AccountRepository accountRepository, ClientExternalService clientExternalService) {
        this.accountRepository = accountRepository;
        this.clientExternalService = clientExternalService;
        this.accountMapper = Mappers.getMapper(AccountMapper.class);
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.convertEntityListToDtoList(accountRepository.findAll());
    }

    @Override
    public AccountDto getById(Long id) {
        return accountMapper.convertEntityToDto(accountRepository.findById(id).orElse(null));
    }

    @Override
    public AccountDto create(AccountDto accountDto) {

        ClientDto clientDto;

        clientDto = clientExternalService.getClient(accountDto.getClientId());

        if (clientDto != null) {
            return accountMapper
                    .convertEntityToDto(accountRepository.save(accountMapper.convertDtoToEntity(accountDto)));
        }

        return null;

    }

    @Override
    public AccountDto update(AccountDto accountDto) {

        ClientDto clientDto;

        clientDto = clientExternalService.getClient(accountDto.getClientId());

        if (clientDto != null) {
            return accountMapper
                    .convertEntityToDto(accountRepository.save(accountMapper.convertDtoToEntity(accountDto)));
        }

        return null;

    }

    @Override
    public AccountDto partialUpdate(Long id, PartialAccountDto partialAccountDto) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null) {
            account.setActive(partialAccountDto.isActive());
            return accountMapper
                    .convertEntityToDto(accountRepository.save(account));
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

}
