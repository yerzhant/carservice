package pl.beck.vehicleworkshop.invoice;

import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Invoice extends BaseEntity {

    private Long id;

    private LocalDateTime created = LocalDateTime.now();

    private List<InvoiceLine> items = new ArrayList<>();
}
