package pl.beck.vehicleworkshop.workorder.domain

import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade
import pl.beck.vehicleworkshop.contracts.domain.ContractServiceFacade
import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade
import pl.beck.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade
import pl.beck.vehicleworkshop.workorder.domain.commandmodel.WorkOrderCreateRequestDto
import spock.lang.Specification

class WorkOrderSpec extends Specification implements SampleWorkOrderData {


    ClientServiceFacade clientServiceFacade = Stub(ClientServiceFacade)
    VehicleServiceFacade vehicleServiceFacade = Stub(VehicleServiceFacade)
    WorkersRegistryServiceFacade workersRegistryServiceFacade = Stub(WorkersRegistryServiceFacade)
    RepairsCatalogServiceFacade repairsCatalogServiceFacade = Stub(RepairsCatalogServiceFacade)
    ContractServiceFacade contractServiceFacade = Stub(ContractServiceFacade)

    WorkOrderServiceFacade workOrderServiceFacade = new Configuration()
            .workOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
            repairsCatalogServiceFacade, contractServiceFacade,)

    def setup() {

        and: "system has client"
        clientServiceFacade.fetchClientData(clientData.personalNumber) >> {
            clientData
        }

        and: "system has client contract"
        contractServiceFacade.fetchContractDataByPersonalNumberAndVin(clientData.personalNumber, vehicleData.vin.value,
                orderCreationTime.toLocalDate(), orderCreationTime.toLocalDate()) >> {
            contractData
        }

        and: "system has vehicle"
        vehicleServiceFacade.fetchVehicleData(vehicleData.vin.value) >> {
            vehicleData
        }

        and: "system has worker"
        workersRegistryServiceFacade.fetchWorkerDataByPersonalNumber(workerData.personalNumber) >> {
            workerData
        }

        and: "Catalog contains repair services"
        repairsCatalogServiceFacade.fetchByCatalogNumber(guaranteedRepairCatalogNumber) >> {
            guaranteedRepairData
        }

        repairsCatalogServiceFacade.fetchByCatalogNumber(paidRepairCatalogNumber) >> {
            paidRepairData
        }
    }

    def "should register new order"() {

        given: "we have new order request data"

        WorkOrderCreateRequestDto request = WorkOrderCreateRequestDto.builder()
                .clientPersonalNumber(clientData.personalNumber)
                .vin(vehicleData.vin.value)
                .creationTime(orderCreationTime)
                .build()


        when: "we register new order"
        def workOrderNumber = workOrderServiceFacade.registerNewOrder(request).getNumber()

        then: "system has order with NEW status"
        workOrderServiceFacade.fetchWorkOrderResponseByNumber(workOrderNumber).status == "NEW"
    }


    def "should assign worker to order"() {

        given: "system has order with specified number"
        WorkOrderCreateRequestDto request = WorkOrderCreateRequestDto.builder()
                .clientPersonalNumber(clientData.personalNumber)
                .vin(vehicleData.vin.value)
                .creationTime(orderCreationTime)
                .build()

        def workOrderNumber = workOrderServiceFacade.registerNewOrder(request).getNumber()


        when: "we assign worker to order"
        workOrderServiceFacade.assignWorkerToOrder(workerData.personalNumber, workOrderNumber)

        then: "work order has assigned worker"
        workOrderServiceFacade.fetchWorkOrderDataByNumber(workOrderNumber).workerPersonalNumber == workerData.personalNumber

    }


    def "should register repairs and close order" () {

        given: "system has order with specified number and assigned worker"
        WorkOrderCreateRequestDto request = WorkOrderCreateRequestDto.builder()
                .clientPersonalNumber(clientData.personalNumber)
                .vin(vehicleData.vin.value)
                .creationTime(orderCreationTime)
                .build()

        def workOrderNumber = workOrderServiceFacade.registerNewOrder(request).getNumber()
        workOrderServiceFacade.assignWorkerToOrder(workerData.personalNumber, workOrderNumber)


        when: "worker register repairs and close order"
        workOrderServiceFacade.addRepairToOrder(guaranteedRepairCatalogNumber, workOrderNumber)
        workOrderServiceFacade.addRepairToOrder(paidRepairCatalogNumber, workOrderNumber)
        workOrderServiceFacade.closeOrder(workOrderNumber)


        then:
        def workOrderData = workOrderServiceFacade.fetchWorkOrderDataByNumber(workOrderNumber)
        workOrderData.status == "CLOSED"

    }
}
