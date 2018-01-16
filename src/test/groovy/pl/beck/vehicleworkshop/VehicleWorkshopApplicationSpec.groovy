package pl.beck.vehicleworkshop

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class VehicleWorkshopApplicationSpec extends Specification {


    def "should load context" () {

        expect:
        1 == 1
    }
}
