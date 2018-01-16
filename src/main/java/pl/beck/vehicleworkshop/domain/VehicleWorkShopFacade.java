package pl.beck.vehicleworkshop.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.beck.vehicleworkshop.domain.exceptions.DomainException;
import pl.beck.vehicleworkshop.domain.protocol.ContractCreateRequestDto;
import pl.beck.vehicleworkshop.domain.protocol.ContractResponseDto;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class VehicleWorkShopFacade {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final VehicleRepository vehicleRepository;
    private final ContractFactory contractFactory;

    private final ContractMapper contractMapper = new ContractMapper();

    // Tylko dla potrzeby napisania testów, nie ma nic wspólnego z user story...
    public ContractResponseDto createNewClientContract(ContractCreateRequestDto createRequest) {

        log.info("Create new contract: " + createRequest);

        final Client client = getClientIfExistsOrThrowException(createRequest.getClientId());
        final Vehicle vehicle = getVehicleIfExistsOrThrowException(createRequest.getVehicleVIN());

        /** TODO
         * Dodac sprawdzenie czy samochod wynajety juz przez tego samego klienta/innego klienta
         * Inne sprawdzenia ktore sa potrzebne...
         */

        final Set<ContractFreeRepair> contractFreeRepairs = contractMapper.fromFree(createRequest);
        final Set<ContractPaidRepair> contractPaidRepairs = contractMapper.fromPaid(createRequest);
        /**
         * TODO
         * Nie sprawdzamy czy faktycznie cena jest zgodna z katalogiem.
         */

        Money freeRepairsLimitPrice = new Money(createRequest.getFreeRepairsLimitPrice());
        ContractPeriod contractPeriod = ContractPeriod.from(createRequest.getValidFrom(), createRequest.getValidTo());

        final Contract contract = contractFactory.createContract(client, vehicle, freeRepairsLimitPrice, contractPeriod,
                contractFreeRepairs, contractPaidRepairs);

        contractRepository.save(contract);

        /**
         * TODO ustawic ze samochod jest wynajety lub sprawdzac zawsze gdzies w serwisie...
         */

        return contractMapper.from(contract);
    }

    private Client getClientIfExistsOrThrowException(Long clientId) {
        final Optional<Client> clientOptional = clientRepository.findOne(clientId);
        return clientOptional.orElseThrow(() -> new DomainException("Client with given id not exists!"));
    }

    private Vehicle getVehicleIfExistsOrThrowException(String vehicleVIN) {
        final Optional<Vehicle> vehicleOptional = vehicleRepository.findOne(vehicleVIN);
        return vehicleOptional.orElseThrow(() -> new DomainException("Vehicle with given vin number not exists!"));
    }

    public ContractResponseDto getClientContract(Long contractId) {
        final Contract contract = getContractIfExistsOrThrowException(contractId);
        return contractMapper.from(contract);
    }

    private Contract getContractIfExistsOrThrowException(Long contractId) {
        return contractRepository.findOne(contractId).orElseThrow(() -> new DomainException("Contract not exists!"));
    }
}
