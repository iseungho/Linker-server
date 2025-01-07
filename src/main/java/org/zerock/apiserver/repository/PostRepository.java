package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
