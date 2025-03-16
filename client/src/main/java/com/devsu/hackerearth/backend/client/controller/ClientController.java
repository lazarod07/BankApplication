package com.devsu.hackerearth.backend.client.controller;

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

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> get(@PathVariable Long id) {
		ClientDto clientDto = clientService.getById(id);
		return clientDto != null ? ResponseEntity.status(HttpStatus.OK).body(clientDto)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody ClientDto clientDto) {
		clientDto = clientService.create(clientDto);
		return clientDto != null ? ResponseEntity.status(HttpStatus.CREATED).body(clientDto)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros incorrectos");
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		ClientDto clientDtoTemporal = clientService.getById(id);
		if (clientDtoTemporal != null) {
			clientDto = clientService.update(clientDto);
			return clientDto != null ? ResponseEntity.status(HttpStatus.OK).body(clientDto)
					: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros incorrectos");
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");

	}

	@PatchMapping("/{id}")
	public ResponseEntity<Object> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {

		ClientDto clientDto = clientService.partialUpdate(id, partialClientDto);
		return clientDto != null ? ResponseEntity.status(HttpStatus.OK).body(clientDto) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parametros incorrectos");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		ClientDto clientDtoTemporal = clientService.getById(id);
		if (clientDtoTemporal != null) {
			clientService.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
}
