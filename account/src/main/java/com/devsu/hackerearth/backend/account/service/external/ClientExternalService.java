package com.devsu.hackerearth.backend.account.service.external;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.feing.ClientFeign;
import com.devsu.hackerearth.backend.account.model.dto.ClientDto;

@Service
public class ClientExternalService {

    private final ClientFeign clientFeign;

    public ClientExternalService(ClientFeign clientFeign) {
        this.clientFeign = clientFeign;
    }

    public ClientDto getClient(Long clientId ){
        ClientDto clientDto;

        try {
            clientDto = clientFeign.get(clientId).getBody();
        } catch (Exception e) {
            return null;
        }

        return clientDto;
    }

}
