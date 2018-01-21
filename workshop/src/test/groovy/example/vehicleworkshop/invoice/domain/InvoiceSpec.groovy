package example.vehicleworkshop.invoice.domain

import example.events.domain.EventBus
import example.events.domain.EventSubscriber
import example.vehicleworkshop.contracts.domain.ContractServiceFacade
import spock.lang.Specification

class InvoiceSpec extends Specification implements SampleInvoiceData {

    ContractServiceFacade contractServiceFacade = Stub(ContractServiceFacade)
    InvoiceServiceFacade invoiceServiceFacade = Mock(InvoiceServiceFacade)// new Configuration().invoiceServiceFacade(contractServiceFacade)
    EventSubscriber eventSubscriber = new WorkOrderCloseEventSubscriber(invoiceServiceFacade)

    EventBus eventBus = EventBus.singletonDefaultBus()


    def setup() {
        eventBus.register(eventSubscriber)
    }

    def "should issue invoice after subscribe" () {
        given: "system has contract data of client"
        contractServiceFacade.fetchContractDataByPersonalNumberAndVin(clientData.personalNumber,
                vehicleData.vin.vin, orderCreationTime.toLocalDate(), orderCreationTime.toLocalDate()) >> {
            contractData
        }

        when: "close order even has been published"
        eventBus.publish(workOrderCloseEvent)

        then: "system subscribe on event"
        1 * invoiceServiceFacade.issueInvoice(workOrderCloseEvent)
        //TODO add implementation
    }
}
