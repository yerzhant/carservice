package pl.beck.vehicleworkshop.contracts.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import pl.beck.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;
import pl.beck.vehicleworkshop.sharedkernel.Money;

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
