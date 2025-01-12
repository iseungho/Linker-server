package org.zerock.apiserver.config.Initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.zerock.apiserver.service.RegionService;

@Component
@RequiredArgsConstructor
public class RegionInitializer implements CommandLineRunner {

    private final RegionService regionService;

    @Override
    public void run(String... args) {
        // Parent 없는 Region 추가
        regionService.addRegion("서울", null);
        regionService.addRegion("부산", null);
        regionService.addRegion("대구", null);
        regionService.addRegion("인천", null);
        regionService.addRegion("광주", null);
        regionService.addRegion("대전", null);
        regionService.addRegion("제주", null);
        regionService.addRegion("경남", null);
        regionService.addRegion("경북", null);
        regionService.addRegion("경기", null);
        regionService.addRegion("충남", null);
        regionService.addRegion("충북", null);
        regionService.addRegion("전남", null);
        regionService.addRegion("전북", null);
        regionService.addRegion("강원", null);

        // Parent 있는 Region 추가
        regionService.addRegion("진주", 8L);  // Parent: 경남
        regionService.addRegion("창원", 8L);  // Parent: 경남
        regionService.addRegion("성남", 10L); // Parent: 경기
    }
}
