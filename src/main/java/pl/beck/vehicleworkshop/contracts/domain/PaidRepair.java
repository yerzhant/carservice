package pl.beck.vehicleworkshop.contracts.domain;


import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;
import pl.beck.vehicleworkshop.sharedkernel.Money;

@Getter
class PaidRepair extends BaseEntity {

    private Long id;

    private RepairServiceCatalogNumber repairServiceCatalogNumber;

    private Money negotiatedPrice;

    PaidRepair(RepairServiceCatalogData repairServiceCatalogData, Money negotiatedPrice) {
        this.repairServiceCatalogNumber = repairServiceCatalogData.getRepairServiceCatalogNumber();
        this.negotiatedPrice = negotiatedPrice;
    }
}
