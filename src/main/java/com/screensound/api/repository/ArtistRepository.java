package com.screensound.api.repository;

import com.screensound.api.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
 Boolean existsByNameIgnoreCase(String name);

    Optional<Artist> findByNameIgnoreCaseContaining(String artistName);
}
