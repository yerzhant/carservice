package pl.beck.vehicleworkshop.workorder.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;

@Getter
class WorkOrderItem extends BaseEntity {

    private Long id;

    private RepairServiceCatalogData repairServiceCatalog;

    WorkOrderItem(final RepairServiceCatalogData repairServiceCatalog) {
        this.repairServiceCatalog = repairServiceCatalog;
    }
}
