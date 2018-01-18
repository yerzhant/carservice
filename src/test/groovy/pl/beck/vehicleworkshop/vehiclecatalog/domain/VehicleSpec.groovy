package pl.beck.vehicleworkshop.vehiclecatalog.domain

import pl.beck.vehicleworkshop.vehiclecatalog.domain.commandmodel.AddNewVehicleRequestDto
import spock.lang.Specification

class VehicleSpec extends Specification {

    VehicleServiceFacade vehicleServiceFacade = new Configuration().vehicleServiceFacade()


    def "should add new vehicle to catalog"() {

        given: "we have vehicle request data"
        String vin = "1HGCR2F53EA275060"

        def addNewVehicleRequestDto = AddNewVehicleRequestDto.builder()
                .vin(vin)
                .brand("Opel")
                .model("Vectra")
                .engineCapacity(1998)
                .engineCapacityUnitName("CUBIC_CENTIMETER")
                .engineType("PETROL")
                .build()

        when: "we add vehicle"
        def vehicleData = vehicleServiceFacade.addNewVehicleToSystem(addNewVehicleRequestDto)

        then: "system returns the vehicle we have added"
        vehicleData.vin.value == vin

    }
}
