package example.vehicleworkshop.repairscatalog.domain

import groovy.transform.CompileStatic

@CompileStatic
trait SampleRepairsNames {

    RepairType repairType

    static enum RepairType {

        TURBO_REPAIR, GEARBOX_REPAIR, CLUTCH_REPAIR, TIMING_BELT_REPLACEMENT, STERRING_REPAIR, COOLING_SYSTEM_REPAIR,
        SUSPENSION_REPAIR, EXHAUST_REPAIR, BRAKRES_REPAIR, COMPUTER_DIAGNOSTIC, COMPRESSOR_REPAIR;

    }

}