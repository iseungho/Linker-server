package org.zerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.apiserver.dto.MemberDTO;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.dto.CommentDTO;
import org.zerock.apiserver.service.AdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/members")
    public List<MemberDTO> getAllMembers() {
        return adminService.getAllMembers();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/members/{mno}")
    public Map<String, String> deleteMember(@PathVariable("mno") Long mno) {
        adminService.deleteMember(mno);
        return Map.of("RESULT", "SUCCESS");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        return adminService.getAllPosts();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/posts/{pno}")
    public Map<String, String> deletePost(@PathVariable("pno") Long pno) {
        adminService.deletePost(pno);
        return Map.of("RESULT", "SUCCESS");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/comments")
    public List<CommentDTO> getAllComments() {
        return adminService.getAllComments();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/comments/{id}")
    public Map<String, String> deleteComment(@PathVariable("id") Long id) {
        adminService.deleteComment(id);
        return Map.of("RESULT", "SUCCESS");
    }
}