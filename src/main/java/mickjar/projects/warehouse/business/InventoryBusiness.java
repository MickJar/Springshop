package mickjar.projects.warehouse.business;

import mickjar.projects.warehouse.business.model.Product;
import mickjar.projects.warehouse.integration.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryBusiness {
    private final InventoryRepository inventoryRepository;

    public InventoryBusiness(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Product> GetProducts() {
        return inventoryRepository.GetProducts();
    }

    public boolean SellProduct(String name) {
        var product = inventoryRepository.GetProduct(name);

        for (var article : product.articles()) {
            inventoryRepository.RemoveArticle(article, 0);
        }

        return true;
    }
}
