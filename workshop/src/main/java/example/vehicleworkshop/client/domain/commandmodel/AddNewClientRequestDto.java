package example.vehicleworkshop.client.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddNewClientRequestDto {

    private String personalNumber;

    private String name;

    private String surName;

    private String phoneNumber;
}
