package mickjar.projects.warehouse.integration;

import mickjar.projects.warehouse.integration.model.ArticleInventory;
import mickjar.projects.warehouse.integration.model.ProductDto;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    /**
     * @return All available products definitions
     */
    List<ProductDto> GetProducts();

    /**
     * @param art_id The Article ID
     * @return the amount of stock available
     */
    int getInventoryStock(long art_id);

    /**
     * @param art_id The Article ID
     * @param amount the amount of stock to remove
     * @return true if there was enough stock, otherwise false
     */
    boolean RemoveArticle(long art_id, int amount);

    /**
     * @param name Name of the product to fetch
     * @return an Optional of the product - empty if not found.
     */
    Optional<ProductDto> GetProduct(String name);

    /**
     * @return A list with the available inventory
     */
    List<ArticleInventory> getInventoryStock();
}
