package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Category;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.domain.Member; // Member import 추가
import org.zerock.apiserver.domain.Region;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.repository.CategoryRepository;
import org.zerock.apiserver.repository.PostRepository;
import org.zerock.apiserver.repository.MemberRepository; // MemberRepository import 추가
import org.zerock.apiserver.repository.RegionRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository; // MemberRepository 주입
    private final CategoryRepository categoryRepository;
    private final RegionRepository regionRepository;

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
        Member member = memberRepository.findById(postDTO.getMno())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_MEMBER"));

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_CATEGORY"));

        Region region = regionRepository.findById(postDTO.getRegionId())
                .orElseThrow(() -> new CustomServiceException("NOT_EXIST_REGION"));

        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postType(postDTO.getPostType())
                .member(member)
                .location(postDTO.getLocation())
                .photoUrl(postDTO.getPhotoUrl())
                .category(category) // category 설정
                .region(region) // region 설정
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
                .location(post.getLocation())
                .photoUrl(post.getPhotoUrl())
                .mno(post.getMember().getMno()) // member로부터 mno 가져오기
                .categoryId(post.getCategory() != null ? post.getCategory().getId() : null) // categoryId 설정
                .regionId(post.getRegion() != null ? post.getRegion().getId() : null) // regionId 설정
                .created(post.getCreated()) // created 설정
                .updated(post.getUpdated()) // updated 설정
                .commentCount(commentCount)
                .build();
    }

}
