package example.vehicleworkshop.workorder.domain.event;

import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import lombok.Value;

import java.util.UUID;

@Value
public class WorkOrderCloseEvent {

    private String eventUUID;

    private WorkOrderData workOrderData;

    public WorkOrderCloseEvent(WorkOrderData workOrderData) {
        this.workOrderData = workOrderData;
        this.eventUUID = UUID.randomUUID().toString();
    }
}
