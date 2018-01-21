package example.vehicleworkshop.contracts.domain;

import example.vehicleworkshop.publishedlanguage.ClientData;
import example.vehicleworkshop.publishedlanguage.ContractNumber;

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
