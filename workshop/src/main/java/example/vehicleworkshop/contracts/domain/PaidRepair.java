package example.vehicleworkshop.contracts.domain;


import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;
import lombok.Getter;

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
