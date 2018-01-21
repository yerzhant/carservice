package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class RepairServiceCatalogNumber {

    private String number;
}
