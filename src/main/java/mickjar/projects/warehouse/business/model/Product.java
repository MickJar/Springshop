package mickjar.projects.warehouse.business.model;

import java.util.List;

public record Product(String name, List<Article> articles) {
}
