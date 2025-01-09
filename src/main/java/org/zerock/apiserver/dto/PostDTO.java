package org.zerock.apiserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {
    private Long id;

    private String title;

    private String content;

    private String boardName;

    private String nickname;

    private String regionName;

    private String itemCategoryName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
