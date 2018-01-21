package example.vehicleworkshop.contracts.domain.readmodel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class ClientContractResponseDto {

    private Long id;

    private String conractNumber;

    private String clientPersonalNumber;

    private String vin;

    private double freeRepairsLimitPrice;

    private LocalDate validFrom;

    private LocalDate validTo;

    private Set<GuaranteedRepairDto> guaranteedRepairs;

    private Set<PaidRepairDto> paidRepairs;

    @Data
    @Builder
    public static class GuaranteedRepairDto {

        private String catalogNumber;
    }

    @Data
    @Builder
    public static class PaidRepairDto {

        private String catalogNumber;

        private double negotiatedPrice;
    }
}
