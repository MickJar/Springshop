package mickjar.projects.warehouse.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Article(@JsonProperty("art_id") long id, @JsonProperty("amount_of") int amount) { }
