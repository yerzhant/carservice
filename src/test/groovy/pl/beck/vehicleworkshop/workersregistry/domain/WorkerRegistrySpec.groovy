package pl.beck.vehicleworkshop.workersregistry.domain

import pl.beck.vehicleworkshop.client.domain.commandmodel.AddNewClientRequestDto
import pl.beck.vehicleworkshop.workersregistry.domain.commandmodel.NewWorkerRequestDto
import pl.beck.vehicleworkshop.workersregistry.domain.readmodel.WorkerResponseDataDto
import spock.lang.Specification

class WorkerRegistrySpec extends Specification {

    WorkersRegistryServiceFacade workersRegistryServiceFacade = new Configuration().workersRegistryServiceFacade()


    def "should add new worker to system"() {

        given: "we have worker request data"
        String personalNumber = "8911051012123"

        def newWorkerRequest = NewWorkerRequestDto.builder()
                .personalNumber(personalNumber)
                .build()

        when: "we add worker"
        WorkerResponseDataDto workerResponseData = workersRegistryServiceFacade.registerNewWorker(newWorkerRequest)

        then: "system returns the worker we have added"
        workerResponseData.personalNumber == personalNumber
    }


    def "should fetch worker by given personal number"() {

        given: "we add a worker"
        String personalNumber = "8911051012123"

        def newWorkerRequest = NewWorkerRequestDto.builder()
                .personalNumber(personalNumber)
                .build()

        workersRegistryServiceFacade.registerNewWorker(newWorkerRequest)

        when: "we ask for worker data by his personal number"
        def workerData = workersRegistryServiceFacade.fetchWorkerDataByPersonalNumber(personalNumber)

        then: "system returns the worker we have added"
        workerData.personalNumber == personalNumber
    }
}
