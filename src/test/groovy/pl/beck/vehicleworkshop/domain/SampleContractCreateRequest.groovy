package pl.beck.vehicleworkshop.domain

import groovy.transform.CompileStatic
import pl.beck.vehicleworkshop.domain.protocol.ContractCreateRequestDto

import java.time.LocalDate

@CompileStatic
trait SampleContractCreateRequest {


    ContractCreateRequestDto contractCreateRequestDto =
            ContractCreateRequestDto.builder()
                    .clientId(1)
                    .validFrom(LocalDate.now().minusMonths(1))
                    .validTo(LocalDate.now().plusMonths(1))
                    .vehicleVIN("1HGCP2F37AA088256")
                    .freeRepairsLimitPrice(500.00d)
                    .freeRepairDtos(
                    [
                            ContractCreateRequestDto.ContractFreeRepairDto.builder()
                                    .listPrice(150.00d)
                                    .repairType("COMPUTER_DIAGNOSTIC")
                                    .build(),

                            ContractCreateRequestDto.ContractFreeRepairDto.builder()
                                    .listPrice(250.00d)
                                    .repairType("BRAKRES_REPAIR")
                                    .build()

                    ])
                    .paidRepairDtos(
                    [
                            ContractCreateRequestDto.ContractPaidRepairDto.builder()
                                    .listPrice(300.00d)
                                    .negotiatedPrice(270.00d)
                                    .repairType("CLUTCH_REPAIR")
                                    .build(),

                            ContractCreateRequestDto.ContractPaidRepairDto.builder()
                                    .listPrice(1250.00d)
                                    .negotiatedPrice(1000.00d)
                                    .repairType("GEARBOX_REPAIR")
                                    .build()
                    ])
                    .build()


}