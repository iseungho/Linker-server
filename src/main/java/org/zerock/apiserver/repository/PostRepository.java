package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.apiserver.domain.Post;
import org.zerock.apiserver.repository.search.SearchPostRepository;

public interface PostRepository extends JpaRepository<Post, Long>, SearchPostRepository {
    @Query("SELECT p, count(distinct c) " +
            " FROM Post p " +
            " LEFT OUTER JOIN Comment c ON c.post = p " +
            " WHERE p.pno = :pno" +
            " GROUP BY p")
    Object getPostByPno(@Param("pno") Long pno);

    @Query("SELECT COUNT(p) > 0 FROM Post p WHERE p.title = :title AND p.content = :content")
    boolean existsByTitleAndContent(@Param("title") String title, @Param("content") String content);

}