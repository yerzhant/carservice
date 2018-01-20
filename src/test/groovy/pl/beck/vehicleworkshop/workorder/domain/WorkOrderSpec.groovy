package pl.beck.vehicleworkshop.workorder.domain

import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade
import pl.beck.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade
import pl.beck.vehicleworkshop.workorder.domain.commandmodel.WorkOrderCreateRequestDto
import spock.lang.Specification

import java.time.LocalDateTime

class WorkOrderSpec extends Specification implements SampleClientData, SampleVehicleData, SampleWorkerData {


    ClientServiceFacade clientServiceFacade = Stub(ClientServiceFacade)
    VehicleServiceFacade vehicleServiceFacade = Stub(VehicleServiceFacade)
    WorkersRegistryServiceFacade workersRegistryServiceFacade = Stub(WorkersRegistryServiceFacade)

    WorkOrderServiceFacade workOrderServiceFacade = new Configuration()
            .workOrderServiceFacade(clientServiceFacade, vehicleServiceFacade, workersRegistryServiceFacade)

    def "should register new order"() {

        given: "we have new order request data"

        def clientPersonalNumber = clientData.personalNumber
        def vin = vehicleData.vin.vin

        WorkOrderCreateRequestDto request = WorkOrderCreateRequestDto.builder()
                .clientPersonalNumber(clientPersonalNumber)
                .vin(vin)
                .created(LocalDateTime.now())
                .build()

        and: "system has client"
        clientServiceFacade.fetchClientData(clientPersonalNumber) >> {
            clientData
        }

        and: "system has vehicle"
        vehicleServiceFacade.fetchVehicleData(vin) >> {
            vehicleData
        }

        and: "system has worker"
        workersRegistryServiceFacade.fetchWorkerDataByPersonalNumber(workerData.personalNumber) >> {
            workerData
        }


        when: "we register new order"
        def workOrderNumber = workOrderServiceFacade.registerNewOrder(request).getNumber()

        then: "system has order with NEW status"
        workOrderServiceFacade.fetchWorkOrderResponseByNumber(workOrderNumber).status == "NEW"
    }
}
