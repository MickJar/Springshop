package mickjar.projects.warehouse.integration.model;

import java.util.List;

public record ProductDto(String name, List<ArticleDefinitionDto> contain_articles) {}
