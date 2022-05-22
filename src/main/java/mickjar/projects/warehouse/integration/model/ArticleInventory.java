package mickjar.projects.warehouse.integration.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleInventory {
    @JsonProperty("art_id")
    private long art_id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("stock")
    private int stock;

    public ArticleInventory(long art_id, String name, int stock) {
        this.art_id = art_id;
        this.name = name;
        this.stock = stock;
    }

    public boolean sellInventory(int amount) {
        var stockLeft = stock - amount;
        if (stockLeft < 0) {
            return false;
        }
        stock -= amount;
        return true;
    }

    public long art_id() {
        return art_id;
    }

    public int stock() {
        return stock;
    }
}
