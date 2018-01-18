package pl.beck.vehicleworkshop.repairscatalog.domain;

import lombok.RequiredArgsConstructor;
import pl.beck.vehicleworkshop.publishedlanguage.RepairServiceCatalogData;
import pl.beck.vehicleworkshop.repairscatalog.domain.commandmodel.AddNewServiceToCatalogRequestDto;

@RequiredArgsConstructor
public class RepairsCatalogServiceFacade {

    private final RepairsCatalogRepository repairsCatalogRepository;


    public void addNewRepairServiceToCatalog(AddNewServiceToCatalogRequestDto addNewServiceToCatalogRequest) {

        final String catalogNumber = addNewServiceToCatalogRequest.getCatalogNumber();
        final double listPrice = addNewServiceToCatalogRequest.getListPrice();
        final String name = addNewServiceToCatalogRequest.getName();

        final RepairsCatalog newRepairsCatalogService = new RepairsCatalog(catalogNumber, listPrice, name);

        repairsCatalogRepository.save(newRepairsCatalogService);
    }

    public RepairServiceCatalogData fetchByCatalogNumber(String catalogNumber) {
        return repairsCatalogRepository.findByCatalogNumberOrThrow(catalogNumber).getSnapshot();
    }

}
