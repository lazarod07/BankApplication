package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
		clientMapper = Mappers.getMapper(ClientMapper.class);
	}

	@Override
	public List<ClientDto> getAll() {
		return clientMapper.convertEntityListToDtoList(clientRepository.findAll());
	}

	@Override
	public ClientDto getById(Long id) {
		return clientMapper.convertEntityToDto(clientRepository.findById(id).orElse(null));
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		return clientMapper.convertEntityToDto(clientRepository.save(clientMapper.convertDtoToEntity(clientDto)));
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		return clientMapper.convertEntityToDto(clientRepository.save(clientMapper.convertDtoToEntity(clientDto)));
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id).orElse(null);
		if (client != null) {
			client.setActive(partialClientDto.isActive());
			return clientMapper.convertEntityToDto(clientRepository.save(client));
		}
		return null;
	}

	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
}
