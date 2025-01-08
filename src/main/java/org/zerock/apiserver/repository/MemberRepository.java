package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.zerock.apiserver.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m " +
            " FROM Member m LEFT JOIN FETCH m.profileImage p " +
            " WHERE m.email = :email")
    Member findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query("SELECT m, p " +
            " FROM Member m LEFT JOIN m.profileImage p " +
            " WHERE m.mno = :mno" +
            " GROUP BY m, p")
    Object getMemberByMno(@Param("mno") Long mno);

    Optional<Member> findByNickname(String nickname);
}
