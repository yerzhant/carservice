package example.vehicleworkshop.publishedlanguage;

import lombok.Value;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Value
@Embeddable
public class WorkOrderData {

    private Long id;

    private WorkOrderNumber workOrderNumber;

    private String clientPersonalNumber;

    private String workerPersonalNumber;

    private String vin;

    private String status;

    private List<Item> items = new ArrayList<>();

    void addItems(RepairServiceCatalogData repairServiceCatalog) {
        items.add(createItem(repairServiceCatalog));
    }

    static Item createItem(RepairServiceCatalogData repairServiceCatalog) {
        return new Item(repairServiceCatalog);
    }

    @Value
    static class Item {
        private RepairServiceCatalogData repairService;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }
}
