package pl.beck.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class ClientData {

    private Long id;

    private String personalNumber;

    public ClientData(Long id, String personalNumber) {
        this.id = id;
        this.personalNumber = personalNumber;
    }
}
