package mickjar.projects.warehouse.integration;

import mickjar.projects.warehouse.integration.model.ArticleInventory;
import mickjar.projects.warehouse.integration.model.ProductDto;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    List<ProductDto> GetProducts();
    int getInventoryStock(long art_id);
    boolean RemoveArticle(long art_id, int amount);
    Optional<ProductDto> GetProduct(String name);
    List<ArticleInventory> getInventoryStock();
}
