package mickjar.projects.warehouse.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Product(String name, @JsonProperty("contain_articles") List<Article> articles) {
}
