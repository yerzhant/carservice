package example.vehicleworkshop.client.domain;

import lombok.Getter;
import example.vehicleworkshop.client.domain.readmodel.ClientDataResponseDto;
import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.sharedkernel.BaseEntity;

import java.util.Objects;

@Getter
class Client extends BaseEntity {

    private Long id;

    private String personalNumber;

    private ClientDetails clientDetails;

    Client(String personalNumber, ClientDetails clientDetails) {
        this.personalNumber = Objects.requireNonNull(personalNumber);
        this.clientDetails = Objects.requireNonNull(clientDetails);
    }

    ClientData getSnapshot() {
        return new ClientData(id, personalNumber);
    }

    ClientDataResponseDto toDto() {
        return ClientDataResponseDto.builder()
                .id(id)
                .personalNumber(personalNumber)
                .name(clientDetails.getName())
                .surName(clientDetails.getSurName())
                .phoneNumber(clientDetails.getPhoneNumber())
                .build();
    }

}