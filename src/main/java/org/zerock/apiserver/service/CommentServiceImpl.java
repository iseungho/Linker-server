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
import org.zerock.apiserver.util.CustomServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDTO get(Long cno) {
        Comment comment = commentRepository.findById(cno).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        return entityToDTO(comment);
    }

    @Override
    public List<CommentDTO> getCommentsByPost(Long pno) {
        List<Comment> result = commentRepository.getCommentsByPostOrderByCno(Post.builder().pno(pno).build());
        return result.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public Long register(CommentDTO commentDTO) {
        // Member 엔티티를 조회하여 설정
        Member member = memberRepository.findById(commentDTO.getMno())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));
        // Post 엔티티를 조회하여 설정
        Post post = postRepository.findById(commentDTO.getPostId())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));

        Comment comment = dtoToEntity(commentDTO);
        comment.setMember(member);  // member 필드 설정
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return comment.getCno();
    }

    @Override
    public void modify(CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(commentDTO.getCno()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_COMMENT"));
        comment.changeContent(commentDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void remove(Long cno) {
        if (!commentRepository.existsById(cno)) {
            throw new CustomServiceException("NOT_EXIST_COMMENT");
        }
        commentRepository.deleteById(cno);
    }

    @Override
    public Comment dtoToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .cno(commentDTO.getCno())
                .content(commentDTO.getContent())
                .build();
    }

    @Override
    public CommentDTO entityToDTO(Comment comment) {
        return CommentDTO.builder()
                .cno(comment.getCno())
                .content(comment.getContent())
                .postId(comment.getPost().getPno()) // post에서 postId 가져오기
                .mno(comment.getMember().getMno())
                .created(comment.getCreated()) // created 설정
                .updated(comment.getUpdated()) // updated 설정
                .build();
    }
}