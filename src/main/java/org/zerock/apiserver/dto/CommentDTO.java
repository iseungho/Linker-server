package org.zerock.apiserver.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long cno ,mno;
    private String content;
    private Long postId;
    private LocalDateTime created;
    private LocalDateTime updated;
}