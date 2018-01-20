package pl.beck.vehicleworkshop.workersregistry.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.beck.vehicleworkshop.publishedlanguage.WorkerData;
import pl.beck.vehicleworkshop.workersregistry.domain.commandmodel.NewWorkerRequestDto;
import pl.beck.vehicleworkshop.workersregistry.domain.readmodel.WorkerResponseDataDto;

@RequiredArgsConstructor
@Slf4j
public class WorkersRegistryServiceFacade {

    private final WorkerRepository workerRepository;

    private final WorkerMapper workerMapper;

    public WorkerResponseDataDto registerNewWorker(NewWorkerRequestDto newWorkerRequest) {

        Worker newWorker = new Worker(newWorkerRequest.getPersonalNumber());
        workerRepository.save(newWorker);

        return workerMapper.mapToDto(newWorker);
    }


    public WorkerResponseDataDto fetchByPersonalNumber(String personalNumber) {

        final Worker worker = workerRepository.findByPersonalNumberOrThrow(personalNumber);
        return WorkerResponseDataDto.builder()
                .id(worker.getId())
                .personalNumber(worker.getPersonalNumber())
                .build();
    }

    public WorkerData fetchWorkerDataByPersonalNumber(String personalNumber) {

        final Worker worker = workerRepository.findByPersonalNumberOrThrow(personalNumber);
        return worker.getSnapshot();
    }
}
