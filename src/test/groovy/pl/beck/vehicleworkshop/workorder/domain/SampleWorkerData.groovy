package pl.beck.vehicleworkshop.workorder.domain

import groovy.transform.CompileStatic
import pl.beck.vehicleworkshop.publishedlanguage.WorkerData

@CompileStatic
trait SampleWorkerData {

    WorkerData workerData = new WorkerData(1, "7912051044")
}