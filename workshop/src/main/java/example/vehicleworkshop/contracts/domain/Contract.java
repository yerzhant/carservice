package example.vehicleworkshop.contracts.domain;

import lombok.Getter;
import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.publishedlanguage.ContractData;
import example.vehicleworkshop.publishedlanguage.ContractNumber;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import example.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber;
import example.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.sharedkernel.Money;

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
                .map(pr -> ContractData.createGuaranteedPaidRepair(contractNumber, pr.getRepairServiceCatalogNumber(), pr.isUsed()))
                .collect(Collectors.toList()));

        repairs.addAll(getPaidRepairs().stream()
                .map(gr -> ContractData.createPaidRepair(contractNumber,gr.getNegotiatedPrice(), gr.getRepairServiceCatalogNumber()))
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

    void markGuaranteedRepairAsUsed(RepairServiceCatalogNumber repairServiceCatalogNumber,
                                    WorkOrderNumber workOrderNumber) {
        final GuaranteedRepair guaranteedRepair = guaranteedRepairs.stream()
                .filter(r -> r.getRepairServiceCatalogNumber().equals(repairServiceCatalogNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Contact does not contains given repair!"));
        guaranteedRepair.markAsUsed(workOrderNumber);
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