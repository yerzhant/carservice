package example.vehicleworkshop.invoice.domain;

import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;

class InvoiceLine extends BaseEntity {

    private Long id;

    private Money netto;


    public InvoiceLine(Money netto, String repairServiceCatalogNumber) {
        this.netto = netto;
    }

    //TODO add implementation
}
