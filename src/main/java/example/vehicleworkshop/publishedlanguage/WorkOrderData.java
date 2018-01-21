package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class WorkOrderData {

    private Long id;

    private WorkOrderNumber workOrderNumber;

    private String clientPersonalNumber;

    private String workerPersonalNumber;

    private String vin;

    private String status;

}
