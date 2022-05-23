package mickjar.projects.warehouse.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import mickjar.projects.warehouse.integration.model.ArticleInventory;
import mickjar.projects.warehouse.integration.model.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryRepositoryLocalJson implements InventoryRepository {

    public static final String CLASSPATH_DATA_PRODUCTS_JSON = "classpath:data/products.json";
    public static final String CLASSPATH_DATA_INVENTORY_JSON = "classpath:data/inventory.json";
    private final List<ProductDto> products;

    private final Map<Long, ArticleInventory> articleIndex;

    public InventoryRepositoryLocalJson() throws IOException {
        products = loadObjects(CLASSPATH_DATA_PRODUCTS_JSON, "products", ProductDto.class);
        List<ArticleInventory> articles = loadObjects(CLASSPATH_DATA_INVENTORY_JSON, "inventory", ArticleInventory.class);
        this.articleIndex = articles.stream()
                .collect(Collectors.toMap(ArticleInventory::art_id, Function.identity()));
    }

    private <T> List<T> loadObjects(String path, String topNode, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var productsPath = ResourceUtils.getFile(path).getPath();
        var productFile = new File(productsPath);
        JsonNode node = objectMapper.readTree(productFile);
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.convertValue(node.get(topNode), listType);

    }

    public List<ProductDto> GetProducts() {
        return products;
    }

    public Optional<ProductDto> GetProduct(String name) {
        return products.stream().filter(productDto -> productDto.name().equals(name)).findFirst();
    }

    public int getInventoryStock(long art_id) {
        var articleStock= this.articleIndex.get(art_id);
        if (articleStock != null) {
            return articleStock.stock();
        }
        return 0;
    }

    public boolean RemoveArticle(long art_id, int amount) {
        var articleInventory = this.articleIndex.get(art_id);
        if (articleInventory != null) {
            return articleInventory.sellInventory(amount);
        }
        throw new IllegalArgumentException("No article with that id");
    }

    public List<ArticleInventory> getInventoryStock() {
        return new ArrayList<>(articleIndex.values());
    }
}
