package org.zerock.apiserver.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long cno ,writerId;
    private String content;
    private Long postId;
}