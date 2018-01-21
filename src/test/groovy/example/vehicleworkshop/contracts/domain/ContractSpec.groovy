package example.vehicleworkshop.contracts.domain

import example.vehicleworkshop.client.domain.ClientServiceFacade
import example.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade
import spock.lang.Specification

import java.time.LocalDate

import static example.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto.GuaranteedRepairDto
import static example.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto.PaidRepairDto

class ContractSpec extends Specification implements SampleClientData, SampleVehicleData, SampleRepairServiceCatalogData {

    ClientServiceFacade clientServiceFacade = Mock(ClientServiceFacade)
    VehicleServiceFacade vehicleServiceFacade = Mock(VehicleServiceFacade)
    RepairsCatalogServiceFacade repairsCatalogServiceFacade = Mock(RepairsCatalogServiceFacade)

    ContractServiceFacade contractServiceFacade = new Configuration()
            .contractServiceFacade(clientServiceFacade, vehicleServiceFacade, repairsCatalogServiceFacade)


    def "should sign new contract with existing client in system"() {

        given: "we have sign contract request data"
        String personalNumber = clientData.personalNumber
        String vin = vehicleData.vin.vin
        String guaranteedRepairCatalogNumber = "AA/F/B/1"
        String paidRepairCatalogNumber = "BB/G/A/5"
        double freeRepairsLimitPrice = 1000
        double negotiatedPrice = 50


        def request = SignContractWithClientRequestDto.builder()
                .clientPersonalNumber(clientData.personalNumber)
                .vin(vehicleData.vin.vin)
                .validFrom(LocalDate.now().minusMonths(1))
                .validTo(LocalDate.now().plusMonths(1))
                .freeRepairsLimitPrice(freeRepairsLimitPrice)
                .guaranteedRepairs(
                [
                        GuaranteedRepairDto.builder()
                                .catalogNumber(guaranteedRepairData.repairServiceCatalogNumber.number)
                                .build()
                ] as Set)
                .paidRepairs(
                [
                        PaidRepairDto.builder()
                                .catalogNumber(paidRepairData.repairServiceCatalogNumber.number)
                                .negotiatedPrice(negotiatedPrice)
                                .build()


                ] as Set)
                .build()


        clientServiceFacade.fetchClientData(personalNumber) >> {
            clientData
        }

        vehicleServiceFacade.fetchVehicleData(vin) >> {
           vehicleData
        }

        repairsCatalogServiceFacade.fetchByCatalogNumber(guaranteedRepairCatalogNumber) >> {
            guaranteedRepairData
        }

        repairsCatalogServiceFacade.fetchByCatalogNumber(paidRepairCatalogNumber) >> {
            paidRepairData
        }

        when: "we add contract"
        def response = contractServiceFacade.signContractWithClient(request)

        then: "system returns the contract we have added"
        def response2 = contractServiceFacade.fetchClientContractResponseByNumber(response.getConractNumber())
        response.conractNumber == response2.conractNumber
    }
}