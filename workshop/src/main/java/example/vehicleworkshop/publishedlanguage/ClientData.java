package example.vehicleworkshop.publishedlanguage;

import example.ddd.domain.BaseAggregateRoot;
import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class ClientData {

    private BaseAggregateRoot.AggregateId id;

    private String personalNumber;

    public ClientData(BaseAggregateRoot.AggregateId id, String personalNumber) {
        this.id = id;
        this.personalNumber = personalNumber;
    }
}
