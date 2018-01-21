package example.vehicleworkshop.workorder.domain;

import lombok.Getter;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.sharedkernel.BaseEntity;

@Getter
class WorkOrderItem extends BaseEntity {

    private Long id;

    private RepairServiceCatalogData repairServiceCatalog;

    WorkOrderItem(final RepairServiceCatalogData repairServiceCatalog) {
        this.repairServiceCatalog = repairServiceCatalog;
    }
}
