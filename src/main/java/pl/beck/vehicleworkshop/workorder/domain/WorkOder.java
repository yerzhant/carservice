package pl.beck.vehicleworkshop.workorder.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.ClientData;
import pl.beck.vehicleworkshop.publishedlanguage.VehicleData;
import pl.beck.vehicleworkshop.publishedlanguage.WorkOrderNumber;
import pl.beck.vehicleworkshop.publishedlanguage.WorkerData;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
class WorkOder extends BaseEntity {

    private Long id;

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

    void assignWorker(WorkerData workerData) {
        canAssign();
        this.workerData = workerData;
        this.status = WorkOrderStatus.ASSIGNED;
    }

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
