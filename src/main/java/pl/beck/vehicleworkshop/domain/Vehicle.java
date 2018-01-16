package pl.beck.vehicleworkshop.domain;

import lombok.Getter;

@Getter
class Vehicle extends BaseEntity {

    private String vinNumber; //TODO can be value object...

    public Vehicle(final String vinNumber) {
        this.vinNumber = vinNumber;
    }
}


//TODO add implementation extends BaseEntity, fields etc.