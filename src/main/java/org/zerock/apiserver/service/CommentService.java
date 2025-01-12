package org.zerock.apiserver.service;

import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.dto.CommentDTO;

import java.util.List;

public interface CommentService {
    CommentDTO get(Long cno);

    List<CommentDTO> getCommentsByPost(Long pno);

    Long register(CommentDTO commentDTO);

    void modify(CommentDTO commentDTO);

    void remove(Long id);

    Comment dtoToEntity(CommentDTO commentDTO);

    CommentDTO entityToDTO(Comment comment);
}