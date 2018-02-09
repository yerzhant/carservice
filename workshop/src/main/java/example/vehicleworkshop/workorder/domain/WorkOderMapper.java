package example.vehicleworkshop.workorder.domain;

import example.vehicleworkshop.workorder.domain.readmodel.WorkOrderResponseDto;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

class WorkOderMapper {


    public WorkOrderResponseDto mapToDto(WorkOder workOder) {

        return WorkOrderResponseDto.builder()
                .clientPersonalNumber(workOder.getClientData().getPersonalNumber())
                .vin(workOder.getVehicleData().getVin().getValue())
                .creationTime(workOder.getCreationTime())
                .number(workOder.getWorkOrderNumber().getNumber())
                .status(workOder.getStatus().name())
                .id(workOder.getAggregateId().toString())
                .items(mapWorkOrderItemsToDto(workOder.getItems()))
                .build();

    }

    private List<WorkOrderResponseDto.WorkOrderItemDto> mapWorkOrderItemsToDto(Set<WorkOrderItem> items) {
        return items.stream()
                .map(i -> WorkOrderResponseDto.WorkOrderItemDto.builder()
                        .id(i.getId())
                        .listPrice(i.getRepairServiceCatalog().getListPrice().toDouble())
                        .build())
                .collect(toList());
    }
}
