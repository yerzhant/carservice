package example.vehicleworkshop.workorder.domain

import example.vehicleworkshop.client.domain.ClientServiceFacade
import example.vehicleworkshop.contracts.domain.ContractServiceFacade
import example.vehicleworkshop.publishedlanguage.WorkOrderData
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade
import example.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade
import example.vehicleworkshop.workorder.domain.commandmodel.WorkOrderCreateRequestDto
import example.vehicleworkshop.workorder.domain.event.WorkOrderEventsPublisher
import spock.lang.Specification

class WorkOrderSpec extends Specification implements SampleWorkOrderData {

    ClientServiceFacade clientServiceFacade = Stub(ClientServiceFacade)
    VehicleServiceFacade vehicleServiceFacade = Stub(VehicleServiceFacade)
    WorkersRegistryServiceFacade workersRegistryServiceFacade = Stub(WorkersRegistryServiceFacade)
    RepairsCatalogServiceFacade repairsCatalogServiceFacade = Stub(RepairsCatalogServiceFacade)
    ContractServiceFacade contractServiceFacade = Stub(ContractServiceFacade)

    WorkOrderEventsPublisher workOrderEventsPublisher = Mock(WorkOrderEventsPublisher)

    WorkOrderServiceFacade workOrderServiceFacade = new Configuration()
            .workOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade,
            repairsCatalogServiceFacade, workOrderEventsPublisher, contractServiceFacade)

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


    def "should register repairs, close order and publish event" () {

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


        then: "system has updated work order status, and event has been published"
        def workOrderData = workOrderServiceFacade.fetchWorkOrderDataByNumber(workOrderNumber)
        workOrderData.status == "CLOSED"
        1 * workOrderEventsPublisher.publishWorkOrderCloseEvent( _ as WorkOrderData)

    }
}
