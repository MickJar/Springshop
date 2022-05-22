package mickjar.projects.warehouse.integration.model;

public class ArticleInventory {
    private final long art_id;
    private final String name;
    private int stock;

    public ArticleInventory(long art_id, String name, int stock) {
        this.art_id = art_id;
        this.name = name;
        this.stock = stock;
    }
}
