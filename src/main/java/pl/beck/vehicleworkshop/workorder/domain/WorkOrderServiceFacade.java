package pl.beck.vehicleworkshop.workorder.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.beck.vehicleworkshop.client.domain.ClientServiceFacade;
import pl.beck.vehicleworkshop.contracts.domain.ContractServiceFacade;
import pl.beck.vehicleworkshop.publishedlanguage.ClientData;
import pl.beck.vehicleworkshop.publishedlanguage.ContractData;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleData;
import pl.beck.vehicleworkshop.publishedlanguage.WorkOrderData;
import pl.beck.vehicleworkshop.publishedlanguage.WorkerData;
import pl.beck.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import pl.beck.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import pl.beck.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;
import pl.beck.vehicleworkshop.workorder.domain.commandmodel.WorkOrderCreateRequestDto;
import pl.beck.vehicleworkshop.workorder.domain.readmodel.WorkOrderResponseDto;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class WorkOrderServiceFacade {

    private final ClientServiceFacade clientServiceFacade;
    private final VehicleServiceFacade vehicleServiceFacade;
    private final WorkersRegistryServiceFacade workersRegistryServiceFacade;
    private final RepairsCatalogServiceFacade repairsCatalogServiceFacade;
    private final ContractServiceFacade contractServiceFacade;

    private final WorkOrderRepository workOrderRepository;
    private final WorkOderMapper workOderMapper;

    public WorkOrderResponseDto registerNewOrder(WorkOrderCreateRequestDto workOrderCreateRequest) {
        String clientPersonalNumber = workOrderCreateRequest.getClientPersonalNumber();
        ClientData clientData = clientServiceFacade.fetchClientData(clientPersonalNumber);

        String vin = workOrderCreateRequest.getVin();
        VehicleData vehicleData = vehicleServiceFacade.fetchVehicleData(vin);

        WorkOder newWorkOrder = new WorkOder(vehicleData, clientData);

        workOrderRepository.save(newWorkOrder);

        return workOderMapper.mapToDto(newWorkOrder);
    }

    public void assignWorkerToOrder(String personalNumber, String workOrderNumber) {

        log.info("Assign worker {} to order {} ", personalNumber, workOrderNumber);
        WorkOder workOder = workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber);
        WorkerData workerData = workersRegistryServiceFacade.fetchWorkerDataByPersonalNumber(personalNumber);

        workOder.assignWorker(workerData);
        workOrderRepository.save(workOder);
    }

    public void addRepairToOrder(String repairServiceCatalogNumber, String workOrderNumber) {

        WorkOder workOder = workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber);
        ClientData clientData = workOder.getClientData();

        String clientPersonalNumber = clientData.getPersonalNumber();
        String vin = workOder.getVehicleData().getVin().getVin();
        LocalDate day = workOder.getCreationTime().toLocalDate();

        RepairServiceCatalogData repairServiceCatalogData = repairsCatalogServiceFacade.fetchByCatalogNumber(repairServiceCatalogNumber);
        ContractData contractData = contractServiceFacade.fetchContractDataByPersonalNumberAndVin(clientPersonalNumber, vin, day, day);

        workOder.addRepair(repairServiceCatalogData, contractData);
        workOrderRepository.save(workOder);

        final String contractNumber = contractData.getContractNumber().getNumber();
        final String serviceCatalogNumber = repairServiceCatalogData.getRepairServiceCatalogNumber().getNumber();

        contractServiceFacade.markGuaranteedRepairAsUsed(contractNumber, serviceCatalogNumber, workOrderNumber);        // TODO dwa agregaty per transakacja !!! Event?
    }

    public void closeOrder(String workOrderNumber) {
        WorkOder workOder = workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber);
        workOder.close();
        workOrderRepository.save(workOder);
        emitIssueInvoiceEvent();
    }

    private void emitIssueInvoiceEvent() {
        //TODO implement
    }

    public WorkOrderData fetchWorkOrderDataByNumber(String workOrderNumber) {
        return workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber).getSnapshot();
    }

    public WorkOrderResponseDto fetchWorkOrderResponseByNumber(String workOrderNumber) {
        WorkOder workOder = workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber);
        return workOderMapper.mapToDto(workOder);
    }
}