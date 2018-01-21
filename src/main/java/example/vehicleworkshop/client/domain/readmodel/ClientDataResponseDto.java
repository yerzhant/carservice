package example.vehicleworkshop.client.domain.readmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDataResponseDto {

    private Long id;

    private String personalNumber;

    private String name;

    private String surName;

    private String phoneNumber;
}
