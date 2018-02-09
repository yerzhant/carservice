package example.vehicleworkshop.workorder.domain;

import example.ddd.domain.BaseAggregateRoot;
import example.ddd.domain.DomainRepository;

import java.util.Optional;

interface WorkOrderRepository extends DomainRepository<WorkOder, BaseAggregateRoot.AggregateId> {

    Optional<WorkOder> findOneByWorkOrderNumber(String number);

    WorkOder findOneByWorkOrderNumberOrThrow(String number);

}
