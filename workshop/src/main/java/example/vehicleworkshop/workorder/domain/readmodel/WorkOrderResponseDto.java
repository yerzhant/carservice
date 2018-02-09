package example.vehicleworkshop.workorder.domain.readmodel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class WorkOrderResponseDto {

    private String id;

    private LocalDateTime creationTime;

    private String vin;

    private String clientPersonalNumber;

    private String number;

    private String status;

    List<WorkOrderItemDto> items;

    @Data
    @Builder
    public static class WorkOrderItemDto {

        private Long id;

        private String repairServiceCatalogNumber;

        private double listPrice;
    }
}
