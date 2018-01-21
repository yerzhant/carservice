package example.vehicleworkshop.invoice.domain;

import example.events.domain.EventSubscriber;
import example.vehicleworkshop.contracts.domain.ContractServiceFacade;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import example.vehicleworkshop.workorder.domain.event.WorkOrderCloseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceFacade implements EventSubscriber<WorkOrderCloseEvent>{

    private final ContractServiceFacade contractServiceFacade;
    private final EventSubscriber workOrderCloseEventSubscriber;
    private final InvoiceRepository invoiceRepository;


    @Override
    public void subscribe(final WorkOrderCloseEvent event) {
        final WorkOrderData workOrderData = event.getWorkOrderData();
        //TODO pass more info in evebt or
    }

    public void issueInvoice(String workOrderNumber) {

    }
}
