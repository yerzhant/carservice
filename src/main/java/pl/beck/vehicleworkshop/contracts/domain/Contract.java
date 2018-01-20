package pl.beck.vehicleworkshop.contracts.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.ClientData;
import pl.beck.vehicleworkshop.publishedlanguage.ContractData;
import pl.beck.vehicleworkshop.publishedlanguage.ContractNumber;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;
import pl.beck.vehicleworkshop.sharedkernel.Money;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
class Contract extends BaseEntity {

    private Long id;

    private ContractNumber contractNumber;

    private ClientData clientData;                                                                                              //TODO or replace ClientData (decoupling bounded context)

    private VehicleIdentificationNumber vin;                                                                                            //TODO or replace VehicleData or only Vin number (decoupling bounded context)

    private final Set<GuaranteedRepair> guaranteedRepairs = new HashSet<>();

    private final Set<PaidRepair> paidRepairs = new HashSet<>();

    private Money freeRepairsLimitPrice;

    private ContractPeriod contractPeriod;

    Contract(ContractNumber contractNumber, ClientData clientData, VehicleIdentificationNumber vehicle,
             Money freeRepairsLimitPrice, ContractPeriod contractPeriod) {
        this.contractNumber = Objects.requireNonNull(contractNumber);
        this.clientData = Objects.requireNonNull(clientData);
        this.vin = Objects.requireNonNull(vehicle);
        this.freeRepairsLimitPrice =  Objects.requireNonNull(freeRepairsLimitPrice);
        this.contractPeriod =  Objects.requireNonNull(contractPeriod);

        if(freeRepairsLimitPrice.lessThan(Money.ZERO))
            throw new IllegalArgumentException("Free repairs limit price can not be negative!");
    }

    ContractData getSnapshot() {
        List<ContractData.Repair> repairs = new ArrayList<>();

        repairs.addAll(getGuaranteedRepairs().stream()
                .map(gr -> ContractData.createGuaranteedRepair(contractNumber, gr.getRepairServiceCatalogNumber()))
                .collect(Collectors.toList()));
        repairs.addAll(getPaidRepairs().stream()
                .map(pr -> ContractData.createPaidRepair(contractNumber, pr.getNegotiatedPrice(), pr.getRepairServiceCatalogNumber()))
                .collect(Collectors.toList()));

        return new ContractData(clientData.getPersonalNumber(), vin.getValue(), contractNumber, repairs);
    }


    void addGuaranteedRepair(RepairServiceCatalogData repairServiceCatalog) {

        final Money listPriceOfGivenService = repairServiceCatalog.getListPrice();
        final Money currentSumOfFreeServices = countTotalSumFreeRepairServices();

        final Money expectedTotalPriceAfterAdd = listPriceOfGivenService.add(currentSumOfFreeServices);

        boolean limitExceeded = expectedTotalPriceAfterAdd.greaterThan(freeRepairsLimitPrice);
        if(limitExceeded)
            throw new IllegalArgumentException("Limit of free repairs exceeded!");

        guaranteedRepairs.add(new GuaranteedRepair(repairServiceCatalog));
    }

    void addPaidRepair(Money negotiatedPrice, RepairServiceCatalogData repairServiceCatalog) {
        this.paidRepairs.add(new PaidRepair(repairServiceCatalog, negotiatedPrice));
    }


    Set<GuaranteedRepair> getGuaranteedRepairs() {
        return Collections.unmodifiableSet(guaranteedRepairs);
    }

    Set<PaidRepair> getPaidRepairs() {
        return Collections.unmodifiableSet(paidRepairs);
    }

    private Money countTotalSumFreeRepairServices() {
        return guaranteedRepairs.stream()
                .map(GuaranteedRepair::getListPrice)
                .reduce(Money::add)
                .orElse(Money.ZERO);
    }
}