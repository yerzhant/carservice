package example.vehicleworkshop.repairscatalog.domain

import example.vehicleworkshop.repairscatalog.domain.commandmodel.AddNewServiceToCatalogRequestDto
import spock.lang.Specification

class RepairsCatalogSpec extends Specification implements SampleRepairsNames {

    RepairsCatalogServiceFacade repairsCatalogServiceFacade = new Configuration().repairsCatalogServiceFacade()

    def "should add new repair - service to catalog"() {

        given: "we have repair service request data"
        String catalogNumber = "AA/F/B/1"

        def addNewServiceToCatalogRequestDto = AddNewServiceToCatalogRequestDto.builder()
                .name(SampleRepairsNames.RepairType.BRAKRES_REPAIR.name())
                .catalogNumber(catalogNumber)
                .listPrice(130.00)
                .build()

        when: "we add new service"
        repairsCatalogServiceFacade.addNewRepairServiceToCatalog(addNewServiceToCatalogRequestDto)

        then: "system returns the service we have added"
        repairsCatalogServiceFacade.fetchByCatalogNumber(catalogNumber).repairServiceCatalogNumber.number == catalogNumber

    }
}
