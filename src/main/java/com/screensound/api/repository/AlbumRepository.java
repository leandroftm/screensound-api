package com.screensound.api.repository;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
    boolean existsByTitleIgnoreCaseAndArtistAndIdNot(String title, Artist artist, Long id);
}
