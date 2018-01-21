package example.vehicleworkshop.workorder.domain.event;

import example.events.domain.EventBus;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class WorkOrderEventsPublisher {

    private final EventBus eventBus;

    public void publishWorkOrderCloseEvent(WorkOrderData workOrderData) {
        WorkOrderCloseEvent event = new WorkOrderCloseEvent(workOrderData);
        log.info("Publish WorkOrderCloseEvent{}", event);
        eventBus.publish(event);
    }
}
