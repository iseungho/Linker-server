package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.service.PostService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/found")
public class FoundPostController {

    private final PostService postService;

    @GetMapping("/{pno}")
    public PostDTO get(@PathVariable("pno") Long pno) {
        return postService.get(pno);
    }

    @PostMapping("/")
    public Map<String, Long> register(@RequestBody PostDTO postDTO) {
        postDTO.setPostType("FOUND");
        Long pno = postService.register(postDTO);
        return Map.of("pno", pno);
    }

    @PutMapping("/{pno}")
    public Map<String, String> modify(@PathVariable("pno") Long pno, @RequestBody PostDTO postDTO) {
        postDTO.setPno(pno);
        postService.modify(postDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    @DeleteMapping("/{pno}")
    public Map<String, String> remove(@PathVariable("pno") Long pno) {
        postService.remove(pno);
        return Map.of("RESULT", "SUCCESS");
    }

    @GetMapping("/")
    public PageResponseDTO<PostDTO> getAllFoundPosts(PageRequestDTO pageRequestDTO) {
        return postService.getListByPostType(pageRequestDTO,"FOUND");
    }
}