package example.vehicleworkshop.contracts.domain;

import lombok.Getter;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import example.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;

import java.util.Objects;

@Getter
class GuaranteedRepair extends BaseEntity {

    private Long id;

    private RepairServiceCatalogNumber repairServiceCatalogNumber;

    private Money listPrice;

    private WorkOrderNumber workOrderNumber;

    GuaranteedRepair(RepairServiceCatalogData repairServiceCatalogData) {
        this.repairServiceCatalogNumber = repairServiceCatalogData.getRepairServiceCatalogNumber();
        this.listPrice = repairServiceCatalogData.getListPrice();
    }

    void markAsUsed(WorkOrderNumber workOrderNumber) {
        Objects.requireNonNull(workOrderNumber);
        this.workOrderNumber = workOrderNumber;
    }

    boolean isUsed() {
        return workOrderNumber != null;
    }
}
