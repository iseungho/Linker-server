package org.zerock.apiserver.dto;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionDTO {
    private int id;

    private String name;
}
