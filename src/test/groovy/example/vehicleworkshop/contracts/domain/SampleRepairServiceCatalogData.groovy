package example.vehicleworkshop.contracts.domain

import groovy.transform.CompileStatic
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber
import example.vehicleworkshop.sharedkernel.Money

@CompileStatic
trait SampleRepairServiceCatalogData {

    String guaranteedRepairCatalogNumber = "AA/F/B/1"
    String paidRepairCatalogNumber = "BB/G/A/5"

    RepairServiceCatalogData guaranteedRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(guaranteedRepairCatalogNumber), new Money(30d))

    RepairServiceCatalogData paidRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(paidRepairCatalogNumber), new Money(60d))
}