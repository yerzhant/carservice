package example.vehicleworkshop.contracts.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import example.vehicleworkshop.client.domain.ClientServiceFacade;
import example.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto;
import example.vehicleworkshop.contracts.domain.readmodel.ClientContractResponseDto;
import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.publishedlanguage.ContractData;
import example.vehicleworkshop.publishedlanguage.ContractNumber;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogNumber;
import example.vehicleworkshop.publishedlanguage.VehicleData;
import example.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import example.vehicleworkshop.sharedkernel.Money;
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class ContractServiceFacade {

    private final ClientServiceFacade clientServiceFacade;
    private final VehicleServiceFacade vehicleServiceFacade;
    private final RepairsCatalogServiceFacade repairsCatalogServiceFacade;

    private final ContractRepository contractRepository;
    private final ContractNumberGenerator contractNumberGenerator;

    private final ContractMapper contractMapper;

    //TODO defacto to jest wynajecie auta moze Bounded Context - renting?
    public ClientContractResponseDto signContractWithClient(SignContractWithClientRequestDto request) {

        final String clientPersonalNumber = request.getClientPersonalNumber();
        final ClientData clientData = clientServiceFacade.fetchClientData(clientPersonalNumber);

        final String vin = request.getVin();
        final VehicleData vehicleData = vehicleServiceFacade.fetchVehicleData(vin);
        checkVehicleCanBeRented(vin, request.getValidFrom(), request.getValidTo());

        final ContractNumber contractNumber = contractNumberGenerator.generate(clientData);
        final Money freeRepairsLimitPrice = new Money(request.getFreeRepairsLimitPrice());

        ContractPeriod contractPeriod = ContractPeriod.of(request.getValidFrom(), request.getValidTo());

        Contract contract = new Contract(contractNumber, clientData, vehicleData.getVin(), freeRepairsLimitPrice, contractPeriod);

        request.getGuaranteedRepairs().forEach(dto -> {
            RepairServiceCatalogData rd = repairsCatalogServiceFacade.fetchByCatalogNumber(dto.getCatalogNumber());
            contract.addGuaranteedRepair(rd);
        });

        request.getPaidRepairs().forEach(dto -> {
            RepairServiceCatalogData rd = repairsCatalogServiceFacade.fetchByCatalogNumber(dto.getCatalogNumber());
            contract.addPaidRepair(new Money(dto.getNegotiatedPrice()), rd);
        });


        contractRepository.save(contract);
        return contractMapper.mapToDto(contract);
    }

    private void checkVehicleCanBeRented(String vin, LocalDate from, LocalDate to) {
        Optional<Contract> contractOptional = contractRepository.findByVinForDates(vin, from, to);
        contractOptional.ifPresent(c -> {
            throw new RuntimeException("Vehicle is rented by: " + c.getClientData());
        });

    }

    public void markGuaranteedRepairAsUsed(String contractNumber, String repairServiceCatalogNumber, String workOrderNumber) {
        Contract contract = contractRepository.findByContractNumberOrThrow(contractNumber);
        contract.markGuaranteedRepairAsUsed(new RepairServiceCatalogNumber(repairServiceCatalogNumber), new WorkOrderNumber(workOrderNumber));
        contractRepository.save(contract);
    }

    public ClientContractResponseDto fetchClientContractResponseByNumber(String contractNumber) {
        return contractMapper.mapToDto(contractRepository.findByContractNumberOrThrow(contractNumber));
    }

    public ContractData fetchContractDataByPersonalNumberAndVin(String personalNumber, String vin, LocalDate from, LocalDate to) {
        return contractRepository.findByPersonalNumberAndVinForDateOrThrow(personalNumber, vin, from, to).getSnapshot();
    }
}
