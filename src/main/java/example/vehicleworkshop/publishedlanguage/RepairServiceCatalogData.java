package example.vehicleworkshop.publishedlanguage;

import lombok.Value;
import example.vehicleworkshop.sharedkernel.Money;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class RepairServiceCatalogData {

    private final RepairServiceCatalogNumber repairServiceCatalogNumber;

    private final Money listPrice;
}

// TODO dodac klucz bazodanowy... Long id;