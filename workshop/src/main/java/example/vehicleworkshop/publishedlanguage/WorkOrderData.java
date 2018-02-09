package example.vehicleworkshop.publishedlanguage;

import example.ddd.domain.BaseAggregateRoot;
import lombok.Value;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
@Embeddable
public class WorkOrderData {

    private BaseAggregateRoot.AggregateId id;

    private LocalDateTime creationTime;

    private WorkOrderNumber workOrderNumber;

    private String clientPersonalNumber;

    private String workerPersonalNumber;

    private String vin;

    private String status;

    private List<Item> items = new ArrayList<>();

    public void addItems(RepairServiceCatalogData repairServiceCatalog) {
        items.add(createItem(repairServiceCatalog));
    }

    static Item createItem(RepairServiceCatalogData repairServiceCatalog) {
        return new Item(repairServiceCatalog);
    }

    @Value
    public static class Item {
        private RepairServiceCatalogData repairService;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}


//TODO czy ten obiekt ma sens?