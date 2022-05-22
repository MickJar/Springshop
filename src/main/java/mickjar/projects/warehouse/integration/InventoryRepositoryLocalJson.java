package mickjar.projects.warehouse.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mickjar.projects.warehouse.business.model.Article;
import mickjar.projects.warehouse.business.model.Product;
import mickjar.projects.warehouse.integration.model.ArticleDto;
import mickjar.projects.warehouse.integration.model.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InventoryRepository {

    private final List<ProductDto> products;

    private final Map<Long, ArticleDto> articleIndex;

    public InventoryRepository() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var productsPath = ResourceUtils.getFile("classpath:data/products.json").getPath();
        var productFile = new File(productsPath);
        JsonNode productNode = objectMapper.readTree(productFile);
        this.products = objectMapper.convertValue(productNode.get("products"),  new TypeReference<List<ProductDto>>(){});

        var articlesPath = ResourceUtils.getFile("classpath:data/inventory.json").getPath();
        var articlesFile = new File(articlesPath);
        JsonNode articlesnode = objectMapper.readTree(articlesFile);
        var articles = objectMapper.convertValue(articlesnode.get("inventory"),  new TypeReference<List<ArticleDto>>(){});
        this.articleIndex = articles.stream()
                .collect(Collectors.toMap(ArticleDto::art_id, Function.identity()));
    }

    public List<ProductDto> GetProducts() {
        return products;
    }

    public ProductDto GetProduct(String name) {
        return new ProductDto(name, List.of());
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
            if(articleInventory.sellInventory(amount)) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("No article with that id");
    }

    public List<ArticleInventory> getInventoryStock() {
        return articleIndex.entrySet().stream()
                .map(longArticleInventoryEntry -> longArticleInventoryEntry.getValue())
                .collect(Collectors.toList());
    }
}
