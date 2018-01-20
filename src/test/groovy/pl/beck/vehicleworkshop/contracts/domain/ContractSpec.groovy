package pl.beck.vehicleworkshop.contracts.domain

import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade
import pl.beck.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto
import pl.beck.vehicleworkshop.publishedlanguage.ClientData
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber
import pl.beck.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber
import pl.beck.vehicleworkshop.sharedkernel.Money
import pl.beck.vehicleworkshop.publishedlanguage.VehicleData

import static pl.beck.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto.GuaranteedRepairDto
import static pl.beck.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto.PaidRepairDto


import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade
import spock.lang.Specification

import java.time.LocalDate

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