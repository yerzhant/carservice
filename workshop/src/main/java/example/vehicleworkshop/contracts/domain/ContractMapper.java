package example.vehicleworkshop.contracts.domain;

import example.vehicleworkshop.contracts.domain.readmodel.ClientContractResponseDto;
import example.vehicleworkshop.contracts.domain.readmodel.ClientContractResponseDto.GuaranteedRepairDto;
import example.vehicleworkshop.contracts.domain.readmodel.ClientContractResponseDto.PaidRepairDto;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

class ContractMapper {

    ClientContractResponseDto mapToDto(Contract contract) {
       return ClientContractResponseDto.builder()
                .id(contract.getAggregateId().toString())
                .clientPersonalNumber(contract.getClientData().getPersonalNumber())
                .vin(contract.getVin().getValue())
                .conractNumber(contract.getContractNumber().getNumber())
                .validFrom(contract.getContractPeriod().getValidFrom())
                .validTo(contract.getContractPeriod().getValidTo())
                .guaranteedRepairs(mapGuaranteedRepairsToDto(contract.getGuaranteedRepairs()))
                .paidRepairs(mapPaidRepairsToDto(contract.getPaidRepairs()))
                .freeRepairsLimitPrice(contract.getFreeRepairsLimitPrice().toDouble())
                .build();
    }

    private Set<GuaranteedRepairDto> mapGuaranteedRepairsToDto(Set<GuaranteedRepair> guaranteedRepairs) {
        return guaranteedRepairs.stream()
                .map(r -> GuaranteedRepairDto
                        .builder()
                        .catalogNumber(r.getRepairServiceCatalogNumber().getNumber())
                        .build())
                .collect(toSet());
    }

    private Set<PaidRepairDto> mapPaidRepairsToDto(Set<PaidRepair> paidRepairs) {
        return paidRepairs.stream()
                .map(r -> PaidRepairDto
                        .builder()
                        .catalogNumber(r.getRepairServiceCatalogNumber().getNumber())
                        .negotiatedPrice(r.getNegotiatedPrice().toDouble())
                        .build())
                .collect(toSet());
    }
}
