package mickjar.projects.warehouse.controller;

import mickjar.projects.warehouse.business.InventoryBusiness;
import mickjar.projects.warehouse.business.model.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final InventoryBusiness inventoryBusiness;

    public ArticleController(InventoryBusiness inventoryBusiness) {
        this.inventoryBusiness = inventoryBusiness;
    }

    @GetMapping("/")
    public ResponseEntity<List<Article>> GetAllProducts() {
        var stock = inventoryBusiness.GetStock();
        if (stock.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(stock);
    }

}
