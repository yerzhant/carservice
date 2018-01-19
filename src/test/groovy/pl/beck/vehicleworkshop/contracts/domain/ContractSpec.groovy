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

class ContractSpec extends Specification {

    ClientServiceFacade clientServiceFacade = Mock(ClientServiceFacade)
    VehicleServiceFacade vehicleServiceFacade = Mock(VehicleServiceFacade)
    RepairsCatalogServiceFacade repairsCatalogServiceFacade = Mock(RepairsCatalogServiceFacade)

    ContractServiceFacade contractServiceFacade = new Configuration()
            .contractServiceFacade(clientServiceFacade, vehicleServiceFacade, repairsCatalogServiceFacade)


    def "should sign new contract with existing client im system"() {

        given: "we have sign contract request data"
        String personalNumber = "89110510433"
        String vin = "1HGCR2F53EA275060"
        String guaranteedRepairCatalogNumber = "AA/F/B/1"
        String paidRepairCatalogNumber = "BB/G/A/5"
        double freeRepairsLimitPrice = 1000

        def request = SignContractWithClientRequestDto.builder()
                .clientPersonalNumber(personalNumber)
                .vin(vin)
                .validFrom(LocalDate.now().minusMonths(1))
                .validTo(LocalDate.now().plusMonths(1))
                .freeRepairsLimitPrice(freeRepairsLimitPrice)
                .guaranteedRepairs(
                [
                        GuaranteedRepairDto.builder()
                                .catalogNumber(guaranteedRepairCatalogNumber)
                                .build()
                ] as Set)
                .paidRepairs(
                [
                        PaidRepairDto.builder()
                                .catalogNumber(paidRepairCatalogNumber)
                                .negotiatedPrice(50)
                                .build()


                ] as Set)
                .build()


        clientServiceFacade.fetchClientData(personalNumber) >> {
            new ClientData(1L, personalNumber)
        }

        vehicleServiceFacade.fetchVehicleData(vin) >> {
            new VehicleData(new VehicleIdentificationNumber(vin), "Opel", "Vectra", "PETROL")
        }

        repairsCatalogServiceFacade.fetchByCatalogNumber(guaranteedRepairCatalogNumber) >> {
            new RepairServiceCatalogData(
                    new RepairServiceCatalogNumber(guaranteedRepairCatalogNumber), new Money(30d))
        }

        repairsCatalogServiceFacade.fetchByCatalogNumber(paidRepairCatalogNumber) >> {
            new RepairServiceCatalogData(
                    new RepairServiceCatalogNumber(paidRepairCatalogNumber), new Money(60d))
        }

        when: "we add contract"
        def response = contractServiceFacade.signContractWithClient(request)


        then: "system returns the contract we have added"
        def response2 = contractServiceFacade.fetcClientContractResponseByNumber(response.getConractNumber())
        response.conractNumber == response2.conractNumber
    }
}