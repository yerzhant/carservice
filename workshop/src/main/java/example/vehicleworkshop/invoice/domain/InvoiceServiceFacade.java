package example.vehicleworkshop.invoice.domain;

import example.vehicleworkshop.contracts.domain.ContractServiceFacade;
import example.vehicleworkshop.publishedlanguage.ContractData;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import example.vehicleworkshop.workorder.domain.event.WorkOrderCloseEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
public class InvoiceServiceFacade  {

    private final ContractServiceFacade contractServiceFacade;
    private final InvoiceRepository invoiceRepository;


    InvoiceServiceFacade(ContractServiceFacade contractServiceFacade, InvoiceRepository invoiceRepository) {
        this.contractServiceFacade = contractServiceFacade;
        this.invoiceRepository = invoiceRepository;
    }

    void issueInvoice(WorkOrderCloseEvent event) {
        WorkOrderData workOrderData = event.getWorkOrderData();

        String clientPersonalNumber = workOrderData.getClientPersonalNumber();
        String vin = workOrderData.getVin();
        LocalDate creationDate = workOrderData.getCreationTime().toLocalDate();
        ContractData contractData = contractServiceFacade.fetchContractDataByPersonalNumberAndVin(clientPersonalNumber, vin, creationDate, creationDate);
        // TODO add implementation
    }
}
