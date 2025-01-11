package org.zerock.apiserver.repository.search;

import org.zerock.apiserver.domain.*;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.apiserver.dto.PageRequestDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
public class SearchPostRepositoryImpl extends QuerydslRepositorySupport implements SearchPostRepository {

    public SearchPostRepositoryImpl() {
        super(Post.class);
    }

    private JPQLQuery<Tuple> getBaseQuery() {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        JPQLQuery<Post> query = from(post);

        query.leftJoin(comment).on(comment.post.eq(post));

        return query.select(post, comment.countDistinct()).groupBy(post);
    }

    @Override
    public Page<Object[]> getPagedPosts(PageRequestDTO pageRequestDTO) {

        JPQLQuery<Tuple> tuple = getBaseQuery();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count
        );

    }

    @Override
    public  Page<Object[]> getPagedPostsByPostType(PageRequestDTO pageRequestDTO, String postType) {


        JPQLQuery<Tuple> tuple = getBaseQuery();
        tuple.where(QPost.post.postType.eq(postType));

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize(),
                Sort.by("pno").descending());

        Objects.requireNonNull(this.getQuerydsl()).applyPagination(pageable, tuple);

        List<Tuple> result = tuple.fetch();

        long count = tuple.fetchCount();

        return new PageImpl<>(
                result.stream().map(Tuple::toArray).collect(Collectors.toList()),
                pageable,
                count
        );
    }

}
