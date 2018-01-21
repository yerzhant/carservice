package example.vehicleworkshop.workersregistry.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewWorkerRequestDto {

    private String personalNumber;
}
