package example.vehicleworkshop.invoice.domain;

import example.events.domain.EventSubscriber;
import example.vehicleworkshop.workorder.domain.event.WorkOrderCloseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WorkOrderCloseEventSubscriber implements EventSubscriber<WorkOrderCloseEvent> {

    private final InvoiceServiceFacade invoiceServiceFacade;

    @Override
    public void subscribe(final WorkOrderCloseEvent event) {
        handleEvent(event);
    }

    private void handleEvent(WorkOrderCloseEvent event) {
        invoiceServiceFacade.issueInvoice(event);
    }
}
