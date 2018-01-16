package pl.beck.vehicleworkshop.domain.protocol;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ContractResponseDto {

    private Long contractId;

    private Long clientId;

    private String vehicleVIN;

    private LocalDate validFrom;

    private LocalDate validTo;

    private double freeRepairsLimitPrice;

    private List<ContractFreeRepairDto> freeRepairDtos;

    private List<ContractPaidRepairDto> paidRepairDtos;

    @Data
    @Builder
    public static class ContractFreeRepairDto {

        private double listPrice;

        private String repairType;
    }

    @Data
    @Builder
    public static class ContractPaidRepairDto {

        private double listPrice;

        private double negotiatedPrice;

        private String repairType;
    }


}
