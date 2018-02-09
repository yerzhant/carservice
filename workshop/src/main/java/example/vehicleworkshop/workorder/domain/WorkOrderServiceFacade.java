package example.vehicleworkshop.workorder.domain;

import example.vehicleworkshop.client.domain.ClientServiceFacade;
import example.vehicleworkshop.contracts.domain.ContractServiceFacade;
import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.publishedlanguage.ContractData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.VehicleData;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import example.vehicleworkshop.publishedlanguage.WorkerData;
import example.vehicleworkshop.repairscatalog.domain.RepairsCatalogServiceFacade;
import example.vehicleworkshop.vehiclecatalog.domain.VehicleServiceFacade;
import example.vehicleworkshop.workersregistry.domain.WorkersRegistryServiceFacade;
import example.vehicleworkshop.workorder.domain.commandmodel.WorkOrderCreateRequestDto;
import example.vehicleworkshop.workorder.domain.event.WorkOrderEventsPublisher;
import example.vehicleworkshop.workorder.domain.readmodel.WorkOrderResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class WorkOrderServiceFacade {

    private final ClientServiceFacade clientServiceFacade;
    private final VehicleServiceFacade vehicleServiceFacade;
    private final WorkersRegistryServiceFacade workersRegistryServiceFacade;
    private final RepairsCatalogServiceFacade repairsCatalogServiceFacade;
    private final ContractServiceFacade contractServiceFacade;

    private final WorkOrderEventsPublisher workOrderEventsPublisher;
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
        workOder.close(workOrderEventsPublisher);                                                                       //TODO zrezygnowac z eventu.
        workOrderRepository.save(workOder);
    }

    public WorkOrderData fetchWorkOrderDataByNumber(String workOrderNumber) {
        return workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber).getSnapshot();
    }

    public WorkOrderResponseDto fetchWorkOrderResponseByNumber(String workOrderNumber) {
        WorkOder workOder = workOrderRepository.findOneByWorkOrderNumberOrThrow(workOrderNumber);
        return workOderMapper.mapToDto(workOder);
    }
}
