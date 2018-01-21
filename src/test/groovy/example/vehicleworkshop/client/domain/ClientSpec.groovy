package example.vehicleworkshop.client.domain

import example.vehicleworkshop.client.domain.commandmodel.AddNewClientRequestDto
import spock.lang.Specification

class ClientSpec extends Specification {

    ClientServiceFacade clientServiceFacade = new Configuration().clientServiceFacade()

    def "should add new client to system"() {

        given: "we have client request data"
        String personalNumber = "8911051012123"

        def addNewClientRequestDto = AddNewClientRequestDto.builder()
                .personalNumber(personalNumber)
                .name("Jan")
                .surName("Kowalski")
                .phoneNumber("123123123")
                .build()

        when: "we add client"
        def clientDateResponseDto = clientServiceFacade.addNewClient(addNewClientRequestDto)

        then: "system returns the client we have added"
        clientDateResponseDto.personalNumber == personalNumber
    }


    def "should fetch client by given personal number"() {

        given: "we add a client"
        String personalNumber = "8911051012123"

        def addNewClientRequestDto = AddNewClientRequestDto.builder()
                .personalNumber(personalNumber)
                .name("Jan")
                .surName("Kowalski")
                .phoneNumber("123123123")
                .build()


        def clientDateResponseDto = clientServiceFacade.addNewClient(addNewClientRequestDto)

        when: "we ask for client by his personal number"
        def clientData = clientServiceFacade.fetchClientData(personalNumber)

        then: "system returns the client we have added"
        clientDateResponseDto.personalNumber == personalNumber
        clientData.personalNumber == personalNumber
    }

}
