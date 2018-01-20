package pl.beck.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;

@Value
@Embeddable
public class WorkerData {

    private Long id;

    private String personalNumber;
}
