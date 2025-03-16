package com.devsu.hackerearth.backend.account.feing;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

@FeignClient(name = "Client", url = "http://localhost:8001/api/clients")
public interface ClientFeign {

    @GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id);

}
