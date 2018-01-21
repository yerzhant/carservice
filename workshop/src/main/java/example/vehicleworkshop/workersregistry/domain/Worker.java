package example.vehicleworkshop.workersregistry.domain;

import example.vehicleworkshop.publishedlanguage.WorkerData;
import example.vehicleworkshop.sharedkernel.BaseEntity;
import lombok.Getter;


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