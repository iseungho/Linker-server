package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.service.CommentService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public CommentDTO get(@PathVariable("id") Long id) {
        return commentService.get(id);
    }

    @GetMapping("/post/{pno}")
    public List<CommentDTO> getCommentsByPost(@PathVariable("pno") Long pno) {
        return commentService.getCommentsByPost(pno);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody CommentDTO commentDTO) {
        Long id = commentService.register(commentDTO);
        return Map.of("id", id);
    }

    @PutMapping("/{id}")
    public Map<String, String> modify(@PathVariable("id") Long cno, @RequestBody CommentDTO commentDTO) {
        commentDTO.setCno(cno);
        commentService.modify(commentDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{id}")
    public Map<String, String> remove(@PathVariable("id") Long id) {
        commentService.remove(id);
        return Map.of("RESULT", "SUCCESS");
    }
}