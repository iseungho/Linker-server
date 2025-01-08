package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.apiserver.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
