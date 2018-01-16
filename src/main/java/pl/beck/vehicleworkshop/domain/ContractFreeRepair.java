package pl.beck.vehicleworkshop.domain;

import lombok.Getter;

@Getter
class ContractFreeRepair extends BaseEntity {

    private Long id;

    private final Money listPrice;                                                                                      //cena katalogowa.

    private final RepairType repairType;

    ContractFreeRepair(final Money listPrice, final RepairType repairType) {
        this.listPrice = listPrice;
        this.repairType = repairType;

        if(listPrice.lessThan(Money.ZERO))
            throw new IllegalArgumentException("Price can not be negative!");
    }

    @Override
    public int hashCode() {
        return repairType.hashCode();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ContractFreeRepair that = (ContractFreeRepair) o;

        return repairType == that.repairType;
    }

    @Override
    public String toString() {
        return "ContractFreeRepairService{" + "listPrice=" + listPrice + ", repairType=" + repairType + '}';
    }
}

// TODO zastanowic sie nad lepsza nazwa Service moze byc mylace!!