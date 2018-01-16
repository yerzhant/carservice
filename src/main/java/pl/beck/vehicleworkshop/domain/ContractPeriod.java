package pl.beck.vehicleworkshop.domain;

import java.time.LocalDate;
import java.util.Objects;

class ContractPeriod {

    private final LocalDate validFrom;

    private final LocalDate validTo;

    ContractPeriod(final LocalDate validFrom, final LocalDate validTo) {

        this.validFrom = Objects.requireNonNull(validFrom, "Date validFrom can not be null!!");
        this.validTo = Objects.requireNonNull(validTo, "Date validTo can not be null!!");

        if(!validTo.isAfter(validFrom))
            throw new IllegalArgumentException("Date validTo must be after validFrom!");
    }

    static ContractPeriod of(final LocalDate validFrom, final LocalDate validTo) {
        return new ContractPeriod(validFrom, validTo);
    }

    @Override
    public String toString() {
        return "ContractPeriod{" + "validFrom=" + validFrom + ", validTo=" + validTo + '}';
    }
}
