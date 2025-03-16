package com.devsu.hackerearth.backend.account.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.mapper.TransacctionMapper;
import com.devsu.hackerearth.backend.account.model.Transaction;
import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.model.dto.custom.TransactionAccountDto;
import com.devsu.hackerearth.backend.account.repository.TransactionRepository;
import com.devsu.hackerearth.backend.account.service.external.ClientExternalService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ClientExternalService clientExternalService;

    private final TransacctionMapper transacctionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountService accountService,
    ClientExternalService clientExternalService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.clientExternalService = clientExternalService;
        this.transacctionMapper = Mappers.getMapper(TransacctionMapper.class);
    }

    @Override
    public List<TransactionDto> getAll() {

        return transacctionMapper.convertEntityListToDtoList(transactionRepository.findAll());
    }

    @Override
    public TransactionDto getById(Long id) {
        return transacctionMapper.convertEntityToDto(transactionRepository.findById(id).orElse(null));
    }

    @Override
    public TransactionDto create(TransactionDto transactionDto) {

        AccountDto accountDto = accountService.getById(transactionDto.getAccountId());

        if(accountDto != null){
            Double resultadoOperacion = accountDto.getInitialAmount() + transactionDto.getAmount();

            if (resultadoOperacion < 0) {
                return null;
            }
    
            accountDto.setInitialAmount(resultadoOperacion);
    
            transactionDto.setBalance(resultadoOperacion);
    
            Transaction transaction = transactionRepository.save(transacctionMapper.convertDtoToEntity(transactionDto));
    
            transactionDto = transacctionMapper.convertEntityToDto(transaction);
            accountDto.getTransactions().add(transactionDto);
    
            accountDto = accountService.update(accountDto);
    
            return transactionDto;
        }

        return null;
    }

    @Override
    public List<BankStatementDto> getAllByAccountClientIdAndDateBetween(Long clientId, Date dateTransactionStart, Date dateTransactionEnd) {

        ClientDto clientDto;

        clientDto = clientExternalService.getClient(clientId);
        
        if(clientDto != null){

            List<TransactionAccountDto> transacciones = transactionRepository.getAllByAccountClientIdAndTransactionDateBetween(clientId, dateTransactionStart, dateTransactionEnd);

            List<BankStatementDto> transactionAccountDto  = transacciones.stream().map(ta -> new BankStatementDto(ta.getDate(),clientDto.getName(),ta.getNumber(),ta.getType(),ta.getInitialAmount(), ta.getIsActive(),ta.getTransactionType(), ta.getAmount(), ta.getBalance())).collect(Collectors.toList());

            return transactionAccountDto;
            
        }
        
        return null;
    }

    @Override
    public TransactionDto getLastByAccountId(Long accountId) {
        // If you need it
        return null;
    }

}
