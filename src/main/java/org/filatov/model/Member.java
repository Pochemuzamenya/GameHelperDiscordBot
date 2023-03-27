package org.filatov.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    private Integer id;
    private String discordUsername;

    private Integer steamId;
}
