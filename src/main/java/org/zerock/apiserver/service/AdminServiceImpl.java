package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Member;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.domain.Comment;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Long mno) {
        if (!memberRepository.existsById(mno)) {
            throw new CustomServiceException("NOT_EXIST_MEMBER");
        }
        memberRepository.deleteById(mno);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long pno) {
        if (!postRepository.existsById(pno)) {
            throw new CustomServiceException("NOT_EXIST_POST");
        }
        postRepository.deleteById(pno);
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CustomServiceException("NOT_EXIST_COMMENT");
        }
        commentRepository.deleteById(id);
    }

    private MemberDTO entityToDTO(Member member) {
        return MemberDTO.builder()
                .mno(member.getMno())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }

    private PostDTO entityToDTO(Post post) {
        return PostDTO.builder()
                .pno(post.getPno())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .location(post.getLocation())
                .photoUrl(post.getPhotoUrl())
                .mno(post.getMember().getMno()) // member로부터 mno 가져오기
                .categoryId(post.getCategory() != null ? post.getCategory().getId() : null) // categoryId 설정
                .regionId(post.getRegion() != null ? post.getRegion().getId() : null) // regionId 설정
                .created(post.getCreated()) // created 설정
                .updated(post.getUpdated()) // updated 설정
                .build();
    }

    private CommentDTO entityToDTO(Comment comment) {
        return CommentDTO.builder()
                .cno(comment.getCno())
                .content(comment.getContent())
                .writerId(comment.getMember().getMno()) // member에서 mno 가져오기
                .postId(comment.getPost().getPno()) // post에서 postId 가져오기
                .build();
    }
}