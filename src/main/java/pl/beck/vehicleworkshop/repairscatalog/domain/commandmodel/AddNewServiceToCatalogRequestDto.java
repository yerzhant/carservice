package pl.beck.vehicleworkshop.repairscatalog.domain.commandmodel;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddNewServiceToCatalogRequestDto {

    private String catalogNumber;

    private double listPrice;

    private String name;

}
