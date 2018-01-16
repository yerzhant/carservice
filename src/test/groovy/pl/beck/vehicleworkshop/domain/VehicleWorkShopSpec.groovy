package pl.beck.vehicleworkshop.domain

import spock.lang.Specification

class VehicleWorkShopSpec extends Specification implements SampleContractCreateRequest {


    ContractRepository contractRepository = new ContractRepositoryInMemoryImpl()
    ClientRepository clientRepository = Stub(ClientRepository)
    VehicleRepository vehicleRepository = Stub(VehicleRepository)

    VehicleWorkShopFacade vehicleWorkShopFacade = new Configuration().vehicleWorkShopFacade(contractRepository, clientRepository, vehicleRepository)

    def "should create new client contract"() {

        given: "system has client and vehicle"
        def clientPersonalNumber = "89110510533"
        clientRepository.findOne(contractCreateRequestDto.getClientId()) >> Optional.of(new Client(clientPersonalNumber))
        vehicleRepository.findOne(contractCreateRequestDto.getVehicleVIN()) >> Optional.of(new Vehicle(contractCreateRequestDto.getVehicleVIN()))


        when: "we create new client contract"
        def contract = vehicleWorkShopFacade.createNewClientContract(contractCreateRequestDto)

        then: "system has the client contract"
        vehicleWorkShopFacade.getClientContract(contract.getContractId()).getVehicleVIN() == contractCreateRequestDto.getVehicleVIN()

    }
}
