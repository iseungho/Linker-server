package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.domain.Member; // Member import 추가
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.repository.MemberRepository; // MemberRepository import 추가
import org.zerock.apiserver.util.CustomServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository; // MemberRepository 주입

    @Override
    public PostDTO get(Long pno) {
        Object result = postRepository.getPostByPno(pno);
        if (result == null) {
            throw new CustomServiceException("NOT_EXIST_POST");
        }
        Object[] arr = (Object[]) result;
        return entityToDTO((Post) arr[0], ((Number) arr[1]).intValue());
    }

    @Override
    public PageResponseDTO<PostDTO> getListByPostType(PageRequestDTO pageRequestDTO, String postType) {
        Page<Object[]> result;

        result = postRepository.getPagedPostsByPostType(pageRequestDTO, postType);


        List<PostDTO> dtoList = result
                .get()
                .map(arr -> entityToDTO((Post) arr[0], ((Number) arr[1]).intValue()))
                .collect(Collectors.toList());

        return PageResponseDTO.<PostDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(result.getTotalElements())
                .build();
    }

    @Override
    public Long register(PostDTO postDTO) {
        // 회원 정보를 가져옵니다.
        Member member = memberRepository.findById(postDTO.getMno())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));

        // Post 객체를 생성할 때 member를 설정합니다.
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .member(member) // member 설정
                .created(LocalDateTime.now())
                .build();

        postRepository.save(post);
        return post.getPno();
    }

    @Override
    public void modify(PostDTO postDTO) {
        Post post = postRepository.findById(postDTO.getPno()).orElseThrow(() -> new CustomServiceException("NOT_EXIST_POST"));
        post.changeTitle(postDTO.getTitle());
        post.changeContent(postDTO.getContent());
        postRepository.save(post);
    }

    @Override
    public void remove(Long pno) {
        if (!postRepository.existsById(pno)) {
            throw new CustomServiceException("NOT_EXIST_POST");
        }
        postRepository.deleteById(pno);
    }

    @Override
    public Post dtoToEntity(PostDTO postDTO) {
        return Post.builder()
                .pno(postDTO.getPno())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .created(LocalDateTime.now())
                .build();
    }

    @Override
    public PostDTO entityToDTO(Post post, int commentCount) {
        return PostDTO.builder()
                .pno(post.getPno())
                .title(post.getTitle())
                .content(post.getContent())
                .postType(post.getPostType())
                .commentCount(commentCount)
                .build();
    }
}
