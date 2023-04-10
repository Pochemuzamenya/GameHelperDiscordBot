package org.filatov.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DotaHero {
    private Integer id;
    private String name;
    private String localized_name;
    private String primary_attr;
    private String attack_type;
    private List<String> roles = new ArrayList<>();
}
