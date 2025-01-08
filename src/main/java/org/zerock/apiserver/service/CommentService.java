package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public CommentDTO createComment(CommentDTO commentDTO) {
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        Member member = memberRepository.findByNickname(commentDTO.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .post(post)
                .user(member)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.builder()
                .id(savedComment.getId())
                .content(savedComment.getContent())
                .nickname(savedComment.getUser().getNickname())
                .postId(savedComment.getPost().getId())
                .createdAt(savedComment.getCreatedAt())
                .build();
    }

    public CommentDTO getComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getNickname())
                .postId(comment.getPost().getId())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return CommentDTO.builder()
                .id(updatedComment.getId())
                .content(updatedComment.getContent())
                .nickname(updatedComment.getUser().getNickname())
                .postId(updatedComment.getPost().getId())
                .createdAt(updatedComment.getCreatedAt())
                .build();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(comment -> CommentDTO.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .nickname(comment.getUser().getNickname())
                        .postId(comment.getPost().getId())
                        .createdAt(comment.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
