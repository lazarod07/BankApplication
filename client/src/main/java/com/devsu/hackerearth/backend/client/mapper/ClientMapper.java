package com.devsu.hackerearth.backend.client.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

@Mapper
public interface ClientMapper {

    Client convertDtoToEntity(final ClientDto clientDto);

    ClientDto convertEntityToDto(final Client client);

    List<ClientDto> convertEntityListToDtoList(final List<Client> Clients);
}
