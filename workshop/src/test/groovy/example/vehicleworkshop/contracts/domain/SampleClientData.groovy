package example.vehicleworkshop.contracts.domain

import example.ddd.domain.BaseAggregateRoot
import example.vehicleworkshop.publishedlanguage.ClientData

trait SampleClientData {

    BaseAggregateRoot.AggregateId id = BaseAggregateRoot.AggregateId.generate()
    ClientData clientData = new ClientData(id, "89110510333")
}