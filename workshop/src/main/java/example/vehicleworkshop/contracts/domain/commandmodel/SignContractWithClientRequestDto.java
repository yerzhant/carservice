package example.vehicleworkshop.contracts.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class SignContractWithClientRequestDto {
    
    private String clientPersonalNumber;
    
    private String vin;
    
    private double freeRepairsLimitPrice;
    
    private LocalDate validFrom;
    
    private LocalDate validTo;

    private Set<GuaranteedRepairDto> guaranteedRepairs;

    private Set<PaidRepairDto> paidRepairs;

    @Builder
    @Data
    public static class GuaranteedRepairDto {
        
        private String catalogNumber;
    }

    @Builder
    @Data
    public static class PaidRepairDto {

        private String catalogNumber;

        private double negotiatedPrice;
    }
}
