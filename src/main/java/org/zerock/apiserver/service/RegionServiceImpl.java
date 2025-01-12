package org.zerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.apiserver.domain.Region;
import org.zerock.apiserver.repository.RegionRepository;
import org.zerock.apiserver.util.CustomServiceException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public void initializeRegion(Long id, String name, Long parentRegionId) {
        // 중복 확인
        Optional<Region> existingRegion = regionRepository.findByNameAndParentRegionId(name, parentRegionId);
        if (existingRegion.isPresent()) {
            System.out.println("Region '" + name + "' with parentRegionId '" + parentRegionId + "' already exists.");
            return;
        }

        // 부모 Region 찾기
        Region parentRegion = null;
        if (parentRegionId != null) {
            parentRegion = regionRepository.findById(parentRegionId)
                    .orElseThrow(() -> new CustomServiceException("PARENT_REGION_NOT_FOUND"));
        }

        // Region 저장
        Region newRegion = Region.builder()
                .id(id)
                .name(name)
                .parentRegion(parentRegion)
                .build();

        regionRepository.save(newRegion);
        System.out.println("Region '" + name + "' has been initialized.");
    }
}
