package com.devsu.hackerearth.backend.account.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.BankStatementDto;
import com.devsu.hackerearth.backend.account.model.dto.TransactionDto;
import com.devsu.hackerearth.backend.account.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping
	public ResponseEntity<List<TransactionDto>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(transactionService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Long id) {
		TransactionDto transactionDto = transactionService.getById(id);
		return transactionDto != null ? ResponseEntity.status(HttpStatus.OK).body(transactionDto)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacción no encontrada");
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody TransactionDto transactionDto) {
		transactionDto = transactionService.create(transactionDto);
		return transactionDto != null ? ResponseEntity.status(HttpStatus.CREATED).body(transactionDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo no disponible");
	}

	@GetMapping("/client/{clientId}/report")
	public ResponseEntity<Object> report(@PathVariable Long clientId,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionStart,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTransactionEnd) {

		List<BankStatementDto> transactionAccountDto = transactionService
				.getAllByAccountClientIdAndDateBetween(clientId, dateTransactionStart, dateTransactionEnd);
		return transactionAccountDto != null ? ResponseEntity.status(HttpStatus.OK).body(transactionAccountDto)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
	}
}
