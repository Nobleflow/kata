package com.crafteam.kata.convertor;

import com.crafteam.kata.dto.ClientDto;
import com.crafteam.kata.model.Client;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Client entity and ClientDto.
 * 
 * Provides bidirectional mapping to separate domain entities
 * from API data transfer objects.
 */
@Component
public class ClientMapper {

    /**
     * Converts a Client entity to a ClientDto.
     * 
     * @param client the Client entity to convert
     * @return the corresponding ClientDto, or null if input is null
     */
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

    /**
     * Converts a ClientDto to a Client entity.
     * 
     * @param clientDto the ClientDto to convert
     * @return the corresponding Client entity, or null if input is null
     */
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
