package pl.beck.vehicleworkshop.client.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.beck.vehicleworkshop.client.domain.commandmodel.AddNewClientRequestDto;
import pl.beck.vehicleworkshop.client.domain.readmodel.ClientDataResponseDto;
import pl.beck.vehicleworkshop.publishedlanguage.ClientData;

@RequiredArgsConstructor
@Slf4j
public class ClientServiceFacade {

    private final ClientRepository clientRepository;


    public ClientDataResponseDto addNewClient(AddNewClientRequestDto addNewClientRequest) {
        log.info("Add new client {}", addNewClientRequest);
        final String personalNumber = addNewClientRequest.getPersonalNumber();
        final String name = addNewClientRequest.getName();
        final String surName = addNewClientRequest.getSurName();
        final String phoneNumber = addNewClientRequest.getPhoneNumber();
        ClientDetails clientDetails = new ClientDetails(name, surName, phoneNumber);

        final Client newClient = new Client(personalNumber, clientDetails);
        clientRepository.save(newClient);
        return newClient.toDto();
    }

    public ClientData fetchClientData(String personalNumber) {
        log.info("fetch client data by personalNumber {}", personalNumber);
        final Client client = clientRepository.findByPersonalNumberOrThrow(personalNumber);
        return client.getSnapshot();
    }
}
