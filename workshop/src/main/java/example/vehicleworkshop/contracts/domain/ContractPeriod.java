package example.vehicleworkshop.contracts.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
class ContractPeriod {

    private final LocalDate validFrom;

    private final LocalDate validTo;

    ContractPeriod(final LocalDate validFrom, final LocalDate validTo) {

        this.validFrom = Objects.requireNonNull(validFrom, "Date validFrom can not be null!!");
        this.validTo = Objects.requireNonNull(validTo, "Date validTo can not be null!!");

        if(!validTo.isAfter(validFrom))
            throw new IllegalArgumentException("Date validTo must be after validFrom!");
    }

    boolean overlap(ContractPeriod contractPeriod) {
        return contains(contractPeriod.validFrom) || contains(contractPeriod.validTo);
    }

    boolean contains(LocalDate day) {
        return !day.isBefore(validFrom) && !day.isAfter(validTo);
    }

    static ContractPeriod of(LocalDate validFrom, LocalDate validTo) {
        return new ContractPeriod(validFrom, validTo);
    }

    @Override
    public String toString() {
        return "ContractPeriod{" + "validFrom=" + validFrom + ", validTo=" + validTo + '}';
    }
}
