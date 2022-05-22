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

@Service
public class InventoryRepository {

    private List<ProductDto> products;

    private List<ArticleDto> articles;

    public InventoryRepository() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var productsPath = ResourceUtils.getFile("classpath:data/products.json").getPath();
        var productFile = new File(productsPath);
        JsonNode productNode = objectMapper.readTree(productFile);
        this.products = objectMapper.convertValue(productNode.get("products"),  new TypeReference<List<ProductDto>>(){});

        var articlesPath = ResourceUtils.getFile("classpath:data/inventory.json").getPath();
        var articlesFile = new File(articlesPath);
        JsonNode articlesnode = objectMapper.readTree(articlesFile);
        this.articles = objectMapper.convertValue(articlesnode.get("inventory"),  new TypeReference<List<ArticleDto>>(){});
    }

    public List<ProductDto> GetProducts() {
        return products;
    }

    public ProductDto GetProduct(String name) {
        return new ProductDto(name, List.of());
    }

    public boolean RemoveArticle(Article article, int i) throws IllegalStateException {
        throw new IllegalStateException("Not enough articles" + article.id());
    }
}
