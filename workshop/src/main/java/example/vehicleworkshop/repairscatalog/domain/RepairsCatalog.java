package example.vehicleworkshop.repairscatalog.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;
import lombok.Getter;

import java.util.Objects;

@Getter
class RepairsCatalog extends BaseAggregateRoot {

    private RepairServiceCatalogNumber number;

    private Money listPrice;

    private String name;

    RepairsCatalog(RepairServiceCatalogNumber number, Money listPrice, String name) {
        this.number = Objects.requireNonNull(number);
        this.listPrice = Objects.requireNonNull(listPrice);
        this.name = Objects.requireNonNull(name);
    }

    RepairsCatalog(String catalogNumber,double listPrice, String name) {
        this(new RepairServiceCatalogNumber(catalogNumber), new Money(listPrice), name);
    }

    RepairServiceCatalogData getSnapshot() {
        return new RepairServiceCatalogData(number, listPrice);
    }
}

//TODO add validFrom, validTo, country, gross/net price ?

//TODO types:
/**
 Brakes Clutch Engine Exhaust Fuel System Suspension Steering Transmission Electrical Air Conditioning
 */