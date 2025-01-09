package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.repository.CommentRepository;
import org.zerock.apiserver.repository.MemberRepository;
import org.zerock.apiserver.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(member -> MemberDTO.builder()
                        .mno(member.getMno())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .social(member.isSocial())
                        //.profileImageUrl(member.getProfileImage() != null ? member.getProfileImage().getUrl() : null)
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> PostDTO.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .boardName(post.getBoard().getName())
                        .nickname(post.getMember().getNickname())
                        .regionName(post.getRegion() != null ? post.getRegion().getName() : null)
                        .itemCategoryName(post.getItemCategory() != null ? post.getItemCategory().getName() : null)
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
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

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
