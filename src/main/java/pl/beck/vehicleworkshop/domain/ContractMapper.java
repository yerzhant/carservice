package pl.beck.vehicleworkshop.domain;

import pl.beck.vehicleworkshop.domain.protocol.ContractCreateRequestDto;
import pl.beck.vehicleworkshop.domain.protocol.ContractResponseDto;
import pl.beck.vehicleworkshop.domain.protocol.ContractResponseDto.ContractFreeRepairDto;
import pl.beck.vehicleworkshop.domain.protocol.ContractResponseDto.ContractPaidRepairDto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

class ContractMapper {

    Set<ContractFreeRepair> fromFree(ContractCreateRequestDto contractCreateRequestDto) {
        return contractCreateRequestDto.getFreeRepairDtos().stream()
                .map(this::from)
                .collect(toSet());
    }

    private ContractFreeRepair from(ContractCreateRequestDto.ContractFreeRepairDto contractFreeRepairDto) {
        Money listPrice = new Money(contractFreeRepairDto.getListPrice());
        RepairType repairType = RepairType.valueOf(contractFreeRepairDto.getRepairType());
        return new ContractFreeRepair(listPrice, repairType);

    }


    Set<ContractPaidRepair> fromPaid(ContractCreateRequestDto contractCreateRequestDto) {
        return contractCreateRequestDto.getPaidRepairDtos().stream()
                .map(this::from)
                .collect(toSet());
    }

    private ContractPaidRepair from(ContractCreateRequestDto.ContractPaidRepairDto contractPaidRepairDto) {
        Money listPrice = new Money(contractPaidRepairDto.getListPrice());
        Money negotiatedPrice = new Money(contractPaidRepairDto.getNegotiatedPrice());
        RepairType repairType = RepairType.valueOf(contractPaidRepairDto.getRepairType());
        return new ContractPaidRepair(listPrice, negotiatedPrice, repairType);

    }


    ContractResponseDto from(Contract contract) {

        return ContractResponseDto.builder()
                .contractId(contract.getId())
                .clientId(contract.getClient().getId())
                .vehicleVIN(contract.getVehicle().getVinNumber())
                .validFrom(contract.getContractPeriod().getValidFrom())
                .validTo(contract.getContractPeriod().getValidTo())
                .freeRepairsLimitPrice(contract.getFreeRepairsLimitPrice().toDouble())
                .freeRepairDtos(fromFree(contract.getContractFreeRepairs()))
                .paidRepairDtos(fromPaid(contract.getContractPaidRepairs()))
                .build();
    }

    private List<ContractFreeRepairDto> fromFree(Set<ContractFreeRepair> contractFreeRepairs) {
        return contractFreeRepairs.stream()
                .map(r -> ContractFreeRepairDto.builder()
                        .listPrice(r.getListPrice().toDouble())
                        .repairType(r.getRepairType().name())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ContractPaidRepairDto> fromPaid(Set<ContractPaidRepair> contractPaidRepairs) {
        return contractPaidRepairs.stream()
                .map(r -> ContractPaidRepairDto.builder()
                        .listPrice(r.getListPrice().toDouble())
                        .negotiatedPrice(r.getNegotiatedPrice().toDouble())
                        .repairType(r.getRepairType().name())
                        .build())
                .collect(Collectors.toList());
    }



}
