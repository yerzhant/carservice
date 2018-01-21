package example.vehicleworkshop.invoice.domain;

import example.vehicleworkshop.contracts.domain.ContractServiceFacade;

class Configuration {

    InvoiceServiceFacade invoiceServiceFacade(ContractServiceFacade contractServiceFacade) {
        return new InvoiceServiceFacade(contractServiceFacade, new InvoiceRepositoryInMemoryImpl());
    }

}
