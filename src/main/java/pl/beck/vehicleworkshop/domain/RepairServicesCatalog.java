package pl.beck.vehicleworkshop.domain;

import java.time.LocalDate;

class RepairServicesCatalog extends BaseEntity {

    private Long id;

    private Money listPrice;

    private LocalDate validFrom;

    private LocalDate validTo;

    private RepairType repairType;

    private String decription;
}


//TODO add implementation, fields etc.