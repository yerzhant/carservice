package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@Value
public class WorkOrderNumber {

    private final String number;

    public WorkOrderNumber(String number) {
        this.number = number;
    }

    public static WorkOrderNumber generate(VehicleIdentificationNumber vin) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        final String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("YYYY/dd/mm/HH:mm"));
        final String number = formattedDateTime + "/V: " + vin.getValue();
        return new WorkOrderNumber(number);
    }
}
