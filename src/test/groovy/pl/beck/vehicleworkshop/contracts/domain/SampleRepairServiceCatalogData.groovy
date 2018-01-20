package pl.beck.vehicleworkshop.contracts.domain

import groovy.transform.CompileStatic
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber
import pl.beck.vehicleworkshop.sharedkernel.Money

@CompileStatic
trait SampleRepairServiceCatalogData {

    String guaranteedRepairCatalogNumber = "AA/F/B/1"
    String paidRepairCatalogNumber = "BB/G/A/5"

    RepairServiceCatalogData guaranteedRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(guaranteedRepairCatalogNumber), new Money(30d))

    RepairServiceCatalogData paidRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(paidRepairCatalogNumber), new Money(60d))
}