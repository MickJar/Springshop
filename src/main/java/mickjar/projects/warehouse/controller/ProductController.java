package mickjar.projects.warehouse.controller;

import mickjar.projects.warehouse.business.InventoryBusiness;
import mickjar.projects.warehouse.business.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final InventoryBusiness inventoryBusiness;

    public ProductController(InventoryBusiness inventoryBusiness) {
        this.inventoryBusiness = inventoryBusiness;
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> GetAllProducts() {
        var products = inventoryBusiness.GetProducts();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "{name}/sell")
    public ResponseEntity<Object> SellProduct(String name) {
        boolean success = inventoryBusiness.SellProduct(name);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().body("Not enough inventory");
    }
}
