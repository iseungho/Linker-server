package org.zerock.apiserver.service;

import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO get(Long pno);

    PageResponseDTO<PostDTO> getListByPostType(PageRequestDTO pageRequestDTO, String postType);

    Long register(PostDTO postDTO);

    void modify(PostDTO postDTO);

    void remove(Long pno);

    Post dtoToEntity(PostDTO postDTO);

    PostDTO entityToDTO(Post post, int commentCount);

    void initializePosts(List<PostDTO> postDTOList);
}