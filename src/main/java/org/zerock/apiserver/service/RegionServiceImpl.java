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
        Optional<Region> existingRegion = regionRepository.findByNameAndParentRegionId(name, parentRegionId);
        if (existingRegion.isPresent()) {
            System.out.println("Region '" + name + "' with parentRegionId '" + parentRegionId + "' already exists.");
            return;
        }

        Region parentRegion = null;
        if (parentRegionId != null) {
            parentRegion = regionRepository.findById(parentRegionId)
                    .orElseThrow(() -> new CustomServiceException("PARENT_REGION_NOT_FOUND"));
        }

        Region newRegion = Region.builder()
                .name(name)
                .parentRegion(parentRegion)
                .build();

        regionRepository.save(newRegion);
        System.out.println("Region '" + name + "' has been initialized.");
    }

    @Override
    public void addRegion(String name, Long parentRegionId) {
        initializeRegion(null, name, parentRegionId);
    }
}
