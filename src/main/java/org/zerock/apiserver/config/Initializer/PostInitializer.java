package org.zerock.apiserver.config.Initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.zerock.apiserver.dto.PostDTO;
import org.zerock.apiserver.service.PostService;

import java.io.File;
import java.util.List;

@Component
public class PostInitializer implements CommandLineRunner {
    private final PostService postService;

    public PostInitializer(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void run(String... args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // ClassPathResource로 리소스 경로에서 파일 읽기
        ClassPathResource resource = new ClassPathResource("postData.json");

        // InputStream으로 파일 읽기
        List<PostDTO> postDTOList = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<PostDTO>>() {}
        );

        // Post 데이터를 초기화
        postService.initializePosts(postDTOList);
    }
}
