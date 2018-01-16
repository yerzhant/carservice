package pl.beck.vehicleworkshop.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
class ContractPaidRepair extends BaseEntity {

    private Long id;

    private final Money listPrice;

    private final Money negotiatedPrice;

    private final RepairType repairType;

    ContractPaidRepair(final Money listPrice, final Money negotiatedPrice, final RepairType repairType) {
        this.listPrice = Objects.requireNonNull(listPrice, "List price can not be null");
        this.negotiatedPrice = Objects.requireNonNull(negotiatedPrice, "Negotiated price can not be null");
        this.repairType = Objects.requireNonNull(repairType, "RepairType can not be null");

        if(listPrice.lessThan(Money.ZERO))
            throw new IllegalArgumentException("List price can not be negative!");

        if(negotiatedPrice.lessThan(Money.ZERO))
            throw new IllegalArgumentException("Negotiated price can not be negative!");
    }

    @Override
    public int hashCode() {
        return repairType.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ContractPaidRepair that = (ContractPaidRepair) o;

        return repairType == that.repairType;
    }

    @Override
    public String toString() {
        return "ContractPaidRepair{" + "listPrice=" + listPrice + ", negotiatedPrice=" + negotiatedPrice +
                ", repairType=" + repairType + '}';
    }
}

// TODO zastanowic sie nad lepsza nazwa Service moze byc mylace!!