package example.vehicleworkshop.invoice.domain.event;

import example.events.domain.EventSubscriber;
import example.vehicleworkshop.workorder.domain.event.WorkOrderCloseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorkOrderCloseEventSubscriber implements EventSubscriber<WorkOrderCloseEvent> {

    @Override
    public void subscribe(final WorkOrderCloseEvent event) {
        handleEvent(event);
    }

    private void handleEvent(WorkOrderCloseEvent event) {
        log.info("Subscribed event{} ", event);
    }
}
