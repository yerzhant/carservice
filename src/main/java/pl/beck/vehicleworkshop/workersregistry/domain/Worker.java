package pl.beck.vehicleworkshop.workersregistry.domain;

import lombok.Getter;
import pl.beck.vehicleworkshop.publishedlanguage.WorkerData;
import pl.beck.vehicleworkshop.sharedkernel.BaseEntity;


@Getter
class Worker extends BaseEntity {

    private Long id;

    private String personalNumber;


    public Worker(final String personalNumber) {
        this.personalNumber = personalNumber;
    }

    WorkerData getSnapshot() {
        return new WorkerData(this.id, this.personalNumber);
    }
}
/**
 TODO
 private LocalDate activeFrom; //TODO ActivePeriod?

 private LocalDate activeTo;

 WorkerDetails, WorkerContract ect...
 */