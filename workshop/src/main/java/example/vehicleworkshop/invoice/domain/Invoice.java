package example.vehicleworkshop.invoice.domain;

import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Invoice extends BaseEntity {

    private Long id;

    private LocalDateTime creationTime = LocalDateTime.now();

    private ClientData clientData;

    private Money netto;

    private List<InvoiceLine> items = new ArrayList<>();


    void addItem(InvoiceLine invoiceLine) {

    }

    List<InvoiceLine> getItems() {
        return Collections.unmodifiableList(items);
    }
}
