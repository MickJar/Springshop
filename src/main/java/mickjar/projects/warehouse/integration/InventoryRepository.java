package mickjar.projects.warehouse.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mickjar.projects.warehouse.business.model.Article;
import mickjar.projects.warehouse.business.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class InventoryRepository {

    private List<Product> products;

    private List<Article> articles;

    public InventoryRepository() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var productsPath = ResourceUtils.getFile("classpath:data/products.json").getPath();
        var productFile = new File(productsPath);
        JsonNode productNode = objectMapper.readTree(productFile);
        this.products = objectMapper.convertValue(productNode.get("products"),  new TypeReference<List<Product>>(){});

        var articlesPath = ResourceUtils.getFile("classpath:data/articles.json").getPath();
        var articlesFile = new File(articlesPath);
        JsonNode articlesnode = objectMapper.readTree(articlesFile);
        this.articles = objectMapper.convertValue(articlesnode.get("inventory"),  new TypeReference<List<Article>>(){});
    }

    public List<Product> GetProducts() {
        return products;
    }

    public Product GetProduct(String name) {
        return new Product(name, List.of());
    }

    public boolean RemoveArticle(Article article, int i) throws IllegalStateException {
        throw new IllegalStateException("Not enough articles" + article.id());
    }
}
