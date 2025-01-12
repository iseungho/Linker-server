package org.zerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.apiserver.domain.Region;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("SELECT r FROM Region r WHERE r.name = :name AND (:parentId IS NULL OR r.parentRegion.id = :parentId)")
    Optional<Region> findByNameAndParentRegionId(@Param("name") String name, @Param("parentId") Long parentId);
}