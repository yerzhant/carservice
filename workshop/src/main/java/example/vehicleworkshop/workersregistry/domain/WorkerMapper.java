package example.vehicleworkshop.workersregistry.domain;

import example.vehicleworkshop.workersregistry.domain.readmodel.WorkerResponseDataDto;

class WorkerMapper {

    WorkerResponseDataDto mapToDto(Worker worker) {

        return WorkerResponseDataDto.builder()
                .id(worker.getId())
                .personalNumber(worker.getPersonalNumber())
                .build();
    }
}
