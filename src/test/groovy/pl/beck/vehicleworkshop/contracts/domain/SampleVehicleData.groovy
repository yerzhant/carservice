package pl.beck.vehicleworkshop.contracts.domain

import groovy.transform.CompileStatic
import pl.beck.vehicleworkshop.publishedlanguage.VehicleData
import pl.beck.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber

@CompileStatic
trait SampleVehicleData {

    VehicleIdentificationNumber vehicleIdentificationNumber = new VehicleIdentificationNumber("1HGCR2F53EA275060")
    VehicleData vehicleData = new VehicleData(vehicleIdentificationNumber, "Opel", "Vectra", "PETROL")
}