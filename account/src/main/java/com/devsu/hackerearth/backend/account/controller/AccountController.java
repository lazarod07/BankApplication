package com.devsu.hackerearth.backend.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.account.model.dto.AccountDto;
import com.devsu.hackerearth.backend.account.model.dto.PartialAccountDto;
import com.devsu.hackerearth.backend.account.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@GetMapping
	public ResponseEntity<List<AccountDto>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(accountService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Long id) {
		AccountDto accountDto = accountService.getById(id);
		return accountDto != null ? ResponseEntity.status(HttpStatus.OK).body(accountDto)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody AccountDto accountDto) {
		accountDto = accountService.create(accountDto);
		return accountDto != null ? ResponseEntity.status(HttpStatus.CREATED).body(accountDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros incorrectos");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody AccountDto accountDto) {
		AccountDto accountDtoTemporal = accountService.getById(id);
		if (accountDtoTemporal != null) {
			accountDto = accountService.update(accountDto);
			return accountDto != null ? ResponseEntity.status(HttpStatus.OK).body(accountDto)
					: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros incorrectos");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cuenta no encontrada");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> partialUpdate(@PathVariable Long id,
			@RequestBody PartialAccountDto partialAccountDto) {
		AccountDto accountDto = accountService.partialUpdate(id, partialAccountDto);
		return accountDto != null ? ResponseEntity.status(HttpStatus.OK).body(accountDto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paramentros incorrectos");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		AccountDto accountDtoTemporal = accountService.getById(id);
		if (accountDtoTemporal != null) {
			accountService.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
