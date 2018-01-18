package pl.beck.vehicleworkshop.contracts.domain;

import pl.beck.vehicleworkshop.publishedlanguage.ClientData;
import pl.beck.vehicleworkshop.publishedlanguage.ContractNumber;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class ContractNumberGenerator {

    ContractNumber generate(ClientData clientData) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        final String formattedDateTime = currentDateTime.format(DateTimeFormatter.ofPattern("YYYY/dd/mm/HH:mm"));
        final String number = formattedDateTime + "/C: " + clientData.getPersonalNumber();
        return new ContractNumber(number);
    }
}
