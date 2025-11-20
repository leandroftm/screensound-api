package com.screensound.api.repository;

import com.screensound.api.entity.Artist;
import com.screensound.api.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MusicRepository extends JpaRepository<Music, Long>, JpaSpecificationExecutor<Music> {
    boolean existsByTitleIgnoreCaseAndArtistAndIdNot(String title, Artist artist, Long id);
}
