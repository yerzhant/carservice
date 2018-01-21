package example.vehicleworkshop.client.domain;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
class ClientDetails {

    private String name;

    private String surName;

    private String phoneNumber; //Value object TODO...
}
