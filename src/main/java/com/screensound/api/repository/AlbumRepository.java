package com.screensound.api.repository;

import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AlbumRepository extends JpaRepository<Album, Long> {


    Page<Album> findAllByArtist(Artist artist, Pageable pageable);

    Optional<Album> findByTitleIgnoreCaseContaining(String title);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndArtistAndIdNot(String title, Artist artist, Long id);
}
