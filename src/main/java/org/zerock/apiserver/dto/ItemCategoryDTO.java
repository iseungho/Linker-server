package org.zerock.apiserver.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCategoryDTO {
    private int id;

    private String name;

    private String description;
}