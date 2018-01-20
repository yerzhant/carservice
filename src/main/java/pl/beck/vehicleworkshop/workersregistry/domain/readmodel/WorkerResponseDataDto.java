package pl.beck.vehicleworkshop.workersregistry.domain.readmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkerResponseDataDto {

    private Long id;

    private String personalNumber;
}
