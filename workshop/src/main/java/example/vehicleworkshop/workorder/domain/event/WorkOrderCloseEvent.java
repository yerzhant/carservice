package example.vehicleworkshop.workorder.domain.event;

import example.events.domain.Event;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import lombok.Value;

@Value
public class WorkOrderCloseEvent extends Event {

    private final WorkOrderData workOrderData;

    WorkOrderCloseEvent(WorkOrderData workOrderData) {
        super(workOrderData.getId());
        this.workOrderData = workOrderData;
    }
}
