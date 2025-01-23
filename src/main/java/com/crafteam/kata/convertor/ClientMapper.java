package com.crafteam.kata.convertor;


import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }

        ClientDto clientDto = new ClientDto();

        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setAddress(client.getAddress());

        return clientDto;
    }

    public Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }

        Client client = new Client();

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setAddress(clientDto.getAddress());

        return client;
    }
}
