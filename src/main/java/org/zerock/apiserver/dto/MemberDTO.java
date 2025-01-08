package org.zerock.apiserver.dto;

import lombok.*;
import org.zerock.apiserver.domain.MemberRole;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long mno, pino;

    private String email;

    private String password;

    private String nickname;

    private boolean social;

    private MemberRole role;

    private LocalDateTime createdAt;

    private String profileImageUrl;
}
