package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Board;
import org.zerock.apiserver.dto.BoardDTO;
import org.zerock.apiserver.repository.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardDTO createBoard(BoardDTO boardDTO) {
        Board board = Board.builder()
                .name(boardDTO.getName())
                .description(boardDTO.getDescription())
                .build();
        Board savedBoard = boardRepository.save(board);
        return BoardDTO.builder()
                .id(savedBoard.getId())
                .name(savedBoard.getName())
                .description(savedBoard.getDescription())
                .build();
    }

    public BoardDTO getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        return BoardDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .description(board.getDescription())
                .build();
    }

    public BoardDTO updateBoard(Long id, BoardDTO boardDTO) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        board.setName(boardDTO.getName());
        board.setDescription(boardDTO.getDescription());
        Board updatedBoard = boardRepository.save(board);
        return BoardDTO.builder()
                .id(updatedBoard.getId())
                .name(updatedBoard.getName())
                .description(updatedBoard.getDescription())
                .build();
    }

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    public List<BoardDTO> getAllBoards() {
        return boardRepository.findAll()
                .stream()
                .map(board -> BoardDTO.builder()
                        .id(board.getId())
                        .name(board.getName())
                        .description(board.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
