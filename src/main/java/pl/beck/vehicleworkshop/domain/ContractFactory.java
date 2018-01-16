package pl.beck.vehicleworkshop.domain;

import java.util.Set;

class ContractFactory {

    public Contract createContract(Client client, Vehicle vehicle, Money freeRepairsLimitPrice, ContractPeriod contractPeriod,
                                   Set<ContractFreeRepair> contractFreeRepairs,
                                   Set<ContractPaidRepair> contractPaidRepairs) {

        Contract contract = new Contract(client, vehicle, freeRepairsLimitPrice, contractPeriod);
        contractFreeRepairs.forEach(contract::addFreeRepair);
        contractPaidRepairs.forEach(contract::addPaidRepair);

        return contract;
    }
}
