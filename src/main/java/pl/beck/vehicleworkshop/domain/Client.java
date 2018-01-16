package pl.beck.vehicleworkshop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
class Client extends BaseEntity {

    @Setter(AccessLevel.PRIVATE)
    private Long id;

    private String personalNumber;

    Client(final String personalNumber) {
        this.personalNumber = personalNumber;
    }
}


//TODO add implementation extends BaseEntity, fields etc.