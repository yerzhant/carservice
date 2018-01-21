package example.vehicleworkshop.publishedlanguage;

import example.vehicleworkshop.sharedkernel.Money;
import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class RepairServiceCatalogData {

    private final RepairServiceCatalogNumber repairServiceCatalogNumber;

    private final Money listPrice;
}

// TODO dodac klucz bazodanowy... Long id;