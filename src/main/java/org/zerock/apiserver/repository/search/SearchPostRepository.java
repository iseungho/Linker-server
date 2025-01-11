package org.zerock.apiserver.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.apiserver.dto.PageRequestDTO;

public interface SearchPostRepository {

    Page<Object[]> getPagedPosts(PageRequestDTO pageRequestDTO);

    Page<Object[]> getPagedPostsByPostType(PageRequestDTO pageRequestDTO, String postType);

}
