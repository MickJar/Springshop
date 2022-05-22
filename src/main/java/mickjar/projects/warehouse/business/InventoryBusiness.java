package mickjar.projects.warehouse.business;

import mickjar.projects.warehouse.business.model.Article;
import mickjar.projects.warehouse.business.model.Product;
import mickjar.projects.warehouse.integration.InventoryRepository;
import mickjar.projects.warehouse.integration.model.ArticleDefinitionDto;
import mickjar.projects.warehouse.integration.model.ArticleInventory;
import mickjar.projects.warehouse.integration.model.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryBusiness {
    private final InventoryRepository inventoryRepository;

    public InventoryBusiness(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Get all the available product definitions
     * @return product definitions
     */
    public List<Product> GetProducts() {
        var inventoryProducts = inventoryRepository.GetProducts();
        return inventoryProducts.stream().map(this::mapProduct).collect(Collectors.toList());
    }

    private Product mapProduct(ProductDto productDto) {
        List<Article> articles = productDto.contain_articles().stream().map(this::mapArticle).collect(Collectors.toList());
        return new Product(productDto.name(), articles);
    }

    private Article mapArticle(ArticleDefinitionDto articleDefinitionDto) {
        return new Article(articleDefinitionDto.art_id(), articleDefinitionDto.amount_of());
    }

    private Article mapArticle(ArticleInventory articleInventory) {
        return new Article(articleInventory.art_id(), articleInventory.getStock());
    }

    /**
     * Attempt to sell a product if there is enough stock in the inventory
     * TODO - Implement lock in order to prevent selling orders concurrently.
     *
     * @param name The name of the product to sell
     * @return true if the product sold successfully and false if not
     */
    public boolean SellProduct(String name) {
        var productDefinition = inventoryRepository.GetProduct(name);
        if (productDefinition.isPresent()) {
            var product = mapProduct(productDefinition.get());

            for (var article : product.articles()) {
                var stock = inventoryRepository.getInventoryStock(article.id());
                if (stock < article.amount()) {
                    return false;
                }
            }

            for (var article : product.articles()) {
                var success = inventoryRepository.RemoveArticle(article.id(), article.amount());
                if (!success) {
                    // This is where there is need for rollback in the transaction
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    /**
     * @return A list of the available articles and the current stock
     */
    public List<Article> GetStock() {
        var articleInventory = inventoryRepository.getInventoryStock();
        return articleInventory.stream().map(this::mapArticle).collect(Collectors.toList());
    }
}
