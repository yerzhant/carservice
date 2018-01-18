package pl.beck.vehicleworkshop.contracts.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;
import pl.beck.vehicleworkshop.sharedkernel.Money;

@Getter
class GuaranteedRepair extends BaseEntity {

    private Long id;

    private RepairServiceCatalogNumber repairServiceCatalogNumber;

    private Money listPrice;

    public GuaranteedRepair(RepairServiceCatalogData repairServiceCatalogData) {
        this.repairServiceCatalogNumber = repairServiceCatalogData.getRepairServiceCatalogNumber();
        this.listPrice = repairServiceCatalogData.getListPrice();
    }
}
