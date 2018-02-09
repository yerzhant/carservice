package example.vehicleworkshop.workorder.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.publishedlanguage.ContractData;
import example.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import example.vehicleworkshop.publishedlanguage.VehicleData;
import example.vehicleworkshop.publishedlanguage.WorkOrderData;
import example.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import example.vehicleworkshop.publishedlanguage.WorkerData;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import example.vehicleworkshop.workorder.domain.event.WorkOrderEventsPublisher;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
class WorkOder extends BaseAggregateRoot {

    private LocalDateTime creationTime = LocalDateTime.now();

    private VehicleData vehicleData;

    private ClientData clientData;

    enum WorkOrderStatus {
        NEW, ASSIGNED, CLOSED //TODO inne statusy?
    }

    private WorkOrderStatus status;

    private WorkOrderNumber workOrderNumber;

    private Set<WorkOrderItem> items = new LinkedHashSet<>();

    private WorkerData workerData;

    WorkOder(VehicleData vehicleData, ClientData clientData) {
        this.vehicleData = vehicleData;
        this.clientData = clientData;
        this.status = WorkOrderStatus.NEW;
        this.workOrderNumber = WorkOrderNumber.generate(vehicleData.getVin());
    }

    WorkOrderData getSnapshot() {
        final String clientPersonalNumber = clientData.getPersonalNumber();
        final String workerPersonalNumber = workerData.getPersonalNumber();
        final WorkOrderData workOrderData = new WorkOrderData(aggregateId, creationTime, workOrderNumber, clientPersonalNumber,
                workerPersonalNumber, vehicleData.getVin().getValue(), status.name());
        items.forEach(item -> workOrderData.addItems(item.getRepairServiceCatalog()));
        return workOrderData;
    }

    void assignWorker(WorkerData workerData) {
        canAssign();
        this.workerData = workerData;
        this.status = WorkOrderStatus.ASSIGNED;
    }

    void addRepair(RepairServiceCatalogData repairServiceCatalogData, ContractData contractData) {

        verifyClient(contractData);
        verifyVehicle(contractData);
        verifyContractClause(repairServiceCatalogData, contractData);

        items.add(new WorkOrderItem(repairServiceCatalogData));
    }

    void close(WorkOrderEventsPublisher publisher) {

        if(items.isEmpty()) {
            throw new UnsupportedOperationException("Order does not contains any repair");
        }

        status = WorkOrderStatus.CLOSED;
        publisher.publishWorkOrderCloseEvent(getSnapshot());
    }


    private void verifyClient(ContractData contractData) {
        if(!contractData.getClientPersonalNumber().equals(clientData.getPersonalNumber()))
            throw new IllegalArgumentException("Can not add this repair, because client data is incorrect!");
    }

    private void verifyVehicle(ContractData contractData) {
        if(!contractData.getVin().equals(vehicleData.getVin().getValue()))
            throw new IllegalArgumentException("Can not add this repair, because vehicle data is incorrect!");
    }

    private void verifyContractClause(RepairServiceCatalogData repairServiceCatalogData, final ContractData contractData) {
        final ContractData.Repair repair = contractData.getRepairs().stream()
                .filter(r -> r.getCatalogNumber().equals(repairServiceCatalogData.getRepairServiceCatalogNumber()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Contract does not contain this repair service!"));
        if(repair.isGuaranteed() && repair.getUsed()) {
            throw new IllegalArgumentException("Repair is not guaranteed - has been used!");
        }
    }

//    private boolean isFree(RepairServiceCatalogData repairServiceCatalogData, ContractData contractData) {
//        final ContractData.Repair repair = contractData.getRepairs().stream()
//                .filter(r -> r.getCatalogNumber().equals(repairServiceCatalogData.getRepairServiceCatalogNumber()))
//                .findFirst()
//                .get();
//        return repair.isFree();
//    }

    private void canAssign() {
        if(status == WorkOrderStatus.CLOSED) {
            throw new IllegalArgumentException("Order is closed!");
        }
    }

    Set<WorkOrderItem> getItems() {
        return Collections.unmodifiableSet(items);
    }

    public Optional<WorkerData> getWorker() {
        return Optional.ofNullable(workerData);
    }
}
