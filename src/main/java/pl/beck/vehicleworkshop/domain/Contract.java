package pl.beck.vehicleworkshop.domain;

import lombok.Getter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
class Contract extends BaseEntity {

    private Long id;

    private final Client client;                                                                                        //TODO or replace ClientData (decoupling bounded context)

    private final Vehicle vehicle;                                                                                      //TODO or replace VehicleData or only Vin number (decoupling bounded context)

    private final Set<ContractFreeRepair> contractFreeRepairs = new HashSet<>();

    private final Set<ContractPaidRepair> contractPaidRepairs = new HashSet<>();

    private final Money freeRepairsLimitPrice;

    private final ContractPeriod contractPeriod;

    Contract(final Client client, final Vehicle vehicle, final Money freeRepairsLimitPrice, final ContractPeriod contractPeriod) {
        this.client = Objects.requireNonNull(client, "Client can not be null!");;
        this.vehicle = Objects.requireNonNull(vehicle, "Vehicle can not be null!");;
        this.freeRepairsLimitPrice =  Objects.requireNonNull(freeRepairsLimitPrice, "FreeRepairsLimit can not be null!");
        this.contractPeriod =  Objects.requireNonNull(contractPeriod, "ContractPeriod can not be null!");

        if(freeRepairsLimitPrice.lessThan(Money.ZERO))
            throw new IllegalArgumentException("Free repairs limit price can not be negative!");
    }

    void addFreeRepair(ContractFreeRepair contractFreeRepair) {

        final Money listPriceOfGivenService = contractFreeRepair.getListPrice();
        final Money currentSumOfFreeServices = countTotalSumFreeRepairServices();

        final Money expectedTotalPriceAfterAdd = listPriceOfGivenService.add(currentSumOfFreeServices);

        boolean limitExceeded = expectedTotalPriceAfterAdd.greaterThan(freeRepairsLimitPrice);
        if(limitExceeded)
            throw new IllegalArgumentException("Limit from free repairs exceeded!");

        contractFreeRepairs.add(contractFreeRepair);
    }

    private Money countTotalSumFreeRepairServices() {
        return contractFreeRepairs.stream()
                .map(ContractFreeRepair::getListPrice)
                .reduce(Money::add)
                .orElse(Money.ZERO);
    }

    void addPaidRepair(ContractPaidRepair contractPaidRepair) {
        this.contractPaidRepairs.add(contractPaidRepair);
    }

    Set<ContractFreeRepair> getContractFreeRepairs() {
        return Collections.unmodifiableSet(contractFreeRepairs);
    }

    Set<ContractPaidRepair> getContractPaidRepairs() {
        return Collections.unmodifiableSet(contractPaidRepairs);
    }
}


// TODO add implementation: validation in factory or inside this class ?