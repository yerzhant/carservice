package example.vehicleworkshop.invoice.domain;

import example.events.domain.EventBus;
import example.events.domain.EventSubscriber;
import example.vehicleworkshop.invoice.domain.event.WorkOrderCloseEventSubscriber;

class Configuration {

    InvoiceServiceFacade invoiceServiceFacade() {
        EventSubscriber eventSubscriber = new WorkOrderCloseEventSubscriber();
        EventBus.singletonDefaultBus().register(eventSubscriber);
        return new InvoiceServiceFacade(eventSubscriber, new InvoiceRepositoryInMemoryImpl());
    }
}
