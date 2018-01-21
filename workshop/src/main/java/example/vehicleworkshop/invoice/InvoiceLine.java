package example.vehicleworkshop.invoice;

import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;

class InvoiceLine extends BaseEntity {

    private Long id;

    private Money netto;


    public InvoiceLine(Money netto, RepairServiceCatalogData repairService) {
       // repairService.
        this.netto = netto;
    }
}
