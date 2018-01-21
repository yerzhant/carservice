package example.vehicleworkshop.invoice.domain

import example.vehicleworkshop.publishedlanguage.ClientData
import example.vehicleworkshop.publishedlanguage.ContractData
import example.vehicleworkshop.publishedlanguage.ContractNumber
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber
import example.vehicleworkshop.publishedlanguage.VehicleData
import example.vehicleworkshop.publishedlanguage.VehicleIdentificationNumber
import example.vehicleworkshop.publishedlanguage.WorkOrderData
import example.vehicleworkshop.publishedlanguage.WorkOrderNumber
import example.vehicleworkshop.publishedlanguage.WorkerData
import example.vehicleworkshop.sharedkernel.Money
import example.vehicleworkshop.workorder.domain.event.WorkOrderCloseEvent
import groovy.transform.CompileStatic

import java.time.LocalDateTime

@CompileStatic
trait SampleInvoiceData {

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

    ContractNumber contractNumber = new ContractNumber("1")
    List<ContractData.Repair> repairs =
            [
                    ContractData.createPaidRepair(contractNumber, new Money(40d), paidRepairData.repairServiceCatalogNumber),
                    ContractData.createGuaranteedPaidRepair(contractNumber, guaranteedRepairData.repairServiceCatalogNumber, false)


            ] as List

    ContractData contractData =
            new ContractData(clientData.personalNumber, vehicleData.vin.value, new ContractNumber("1"), repairs)


    WorkOrderData workOrderData = new WorkOrderData(1, orderCreationTime, new WorkOrderNumber("1"), clientData.personalNumber,
            workerData.personalNumber, vehicleData.vin.value, "CLOSED")

    WorkOrderCloseEvent workOrderCloseEvent = new WorkOrderCloseEvent(workOrderData)

}