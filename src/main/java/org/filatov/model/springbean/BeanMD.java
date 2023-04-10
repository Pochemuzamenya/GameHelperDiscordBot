package org.filatov.model.springbean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeanMD {
    private String beanName;
    private String beanClassName;
}
