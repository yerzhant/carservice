package pl.beck.vehicleworkshop.contracts.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade;
import pl.beck.vehicleworkshop.contracts.domain.commandmodel.SignContractWithClientRequestDto;
import pl.beck.vehicleworkshop.contracts.domain.readmodel.ClientContractResponseDto;
import pl.beck.vehicleworkshop.publishedlanguage.ClientData;
import pl.beck.vehicleworkshop.publishedlanguage.ContractNumber;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import pl.beck.vehicleworkshop.sharedkernel.Money;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.protocol.VehicleData;

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
        checkVehicleCanBeRented(vehicleData);

        final ContractNumber contractNumber = contractNumberGenerator.generate(clientData);
        final Money freeRepairsLimitPrice = new Money(request.getFreeRepairsLimitPrice());

        ContractPeriod contractPeriod = ContractPeriod.from(request.getValidFrom(), request.getValidTo());

        Contract contract = new Contract(contractNumber, clientData, vehicleData.getVin(), freeRepairsLimitPrice, contractPeriod);

        request.getGuaranteedRepairs().forEach(dto -> {
            RepairServiceCatalogData rd = repairsCatalogServiceFacade.fetchByCatalogNumber(dto.getCatalogNumber());
            contract.addGuarantedRepair(rd);
        });

        request.getPaidRepairs().forEach(dto -> {
            RepairServiceCatalogData rd = repairsCatalogServiceFacade.fetchByCatalogNumber(dto.getCatalogNumber());
            contract.addPaidRepair(new Money(dto.getNegotiatedPrice()), rd);
        });


        contractRepository.save(contract);
        return contractMapper.mapToDto(contract);
    }

    private void checkVehicleCanBeRented(VehicleData vehicleData) {
        //TODO add logic
    }

    public ClientContractResponseDto fetcClientContractResponseByNumber(String contractNumber) {
        return contractMapper.mapToDto(contractRepository.findByContractNumberOrThrow(contractNumber));
    }
}
