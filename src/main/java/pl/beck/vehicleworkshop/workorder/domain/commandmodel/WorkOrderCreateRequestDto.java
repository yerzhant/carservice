package pl.beck.vehicleworkshop.workorder.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WorkOrderCreateRequestDto {

    private String clientPersonalNumber;

    private String vin;

    private LocalDateTime creationTime;
}
