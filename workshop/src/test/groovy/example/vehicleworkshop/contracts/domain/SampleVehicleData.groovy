package example.vehicleworkshop.contracts.domain

import example.vehicleworkshop.publishedlanguage.VehicleData
import example.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber
import groovy.transform.CompileStatic

@CompileStatic
trait SampleVehicleData {

    VehicleIdentificationNumber vehicleIdentificationNumber = new VehicleIdentificationNumber("1HGCR2F53EA275060")
    VehicleData vehicleData = new VehicleData(vehicleIdentificationNumber, "Opel", "Vectra", "PETROL")
}