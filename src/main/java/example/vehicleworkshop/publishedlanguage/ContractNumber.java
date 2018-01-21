package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class ContractNumber {

    private final String number;

    public ContractNumber(final String number) {
        this.number = number;

    }
}
