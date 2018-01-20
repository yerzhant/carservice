package pl.beck.vehicleworkshop.workorder.domain;

import java.util.Optional;

interface WorkOrderRepository {

    void save(WorkOder workOder);

    Optional<WorkOder> findOne(Long id);

    Optional<WorkOder> findOneByWorkOrderNumber(String number);

    WorkOder findOneByWorkOrderNumberOrThrow(String number);

}
