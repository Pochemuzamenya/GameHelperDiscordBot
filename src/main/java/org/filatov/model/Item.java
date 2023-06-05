package org.filatov.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private Integer id;
    private String img;
    private String dname;
    private Integer cost;

}
