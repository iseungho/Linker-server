package org.zerock.apiserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {
    private Long id;

    private Long postId;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;
}
