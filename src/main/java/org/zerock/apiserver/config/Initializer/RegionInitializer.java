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
    public void run(String... args) throws Exception {
        regionService.initializeRegion(1L, "서울", null);  // id: 1, name: 서울, parentRegionId: null
    }
}
