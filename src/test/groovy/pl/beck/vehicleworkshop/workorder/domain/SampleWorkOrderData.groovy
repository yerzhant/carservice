package pl.beck.vehicleworkshop.workorder.domain

import groovy.transform.CompileStatic
import pl.beck.vehicleworkshop.publishedlanguage.*
import pl.beck.vehicleworkshop.sharedkernel.Money

import java.time.LocalDateTime

@CompileStatic
trait SampleWorkOrderData {


    ClientData clientData = new ClientData(1, "89110510533")

    VehicleIdentificationNumber vehicleIdentificationNumber = new VehicleIdentificationNumber("1HGCR2F53EA275060")
    VehicleData vehicleData = new VehicleData(vehicleIdentificationNumber, "Opel", "Vectra", "PETROL")

    WorkerData workerData = new WorkerData(1, "7912051044")

    String guaranteedRepairCatalogNumber = "AA/F/B/1"
    String paidRepairCatalogNumber = "BB/G/A/5"

    RepairServiceCatalogData guaranteedRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(guaranteedRepairCatalogNumber), new Money(30d))

    RepairServiceCatalogData paidRepairData = new RepairServiceCatalogData(
            new RepairServiceCatalogNumber(paidRepairCatalogNumber), new Money(60d))

    LocalDateTime orderCreationTime = LocalDateTime.now()

    WorkOrderData newWorkOrderData = new WorkOrderData(
            1,
            WorkOrderNumber.generate(vehicleIdentificationNumber), clientData.personalNumber, workerData.personalNumber, vehicleData.vin.value, "NEW"
    )

    ContractNumber contractNumber = new ContractNumber("1")
    List<ContractData.Repair> repairs =
            [
                    ContractData.createPaidRepair(contractNumber, new Money(40d), paidRepairData.repairServiceCatalogNumber),
                    ContractData.createGuaranteedPaidRepair(contractNumber, guaranteedRepairData.repairServiceCatalogNumber, false)


            ] as List

    ContractData contractData =
            new ContractData(clientData.personalNumber, vehicleData.vin.value, new ContractNumber("1"), repairs)
}