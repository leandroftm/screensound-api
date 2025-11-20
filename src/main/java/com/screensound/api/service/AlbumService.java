package com.screensound.api.service;

import com.screensound.api.dto.album.AlbumListDTO;
import com.screensound.api.dto.album.AlbumCreateDTO;
import com.screensound.api.dto.album.AlbumUpdateDTO;
import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import com.screensound.api.exceptions.DuplicateResourceException;
import com.screensound.api.exceptions.ResourceNotFoundException;
import com.screensound.api.repository.AlbumRepository;
import com.screensound.api.repository.ArtistRepository;
import com.screensound.api.spec.AlbumSpecs;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;


    @Transactional
    public Long create(AlbumCreateDTO dto) {
        Artist artist = findArtist(dto.artistId());
        Album album = new Album(dto.title(), artist, dto.releaseDate());
        albumRepository.save(album);
        return album.getId();
    }

    public Page<AlbumListDTO> list(Long artistId, Pageable pageable) {
        validateArtist(artistId);
        Specification<Album> spec = AlbumSpecs.byArtistId(artistId);
        return albumRepository.findAll(spec, pageable).
                map(AlbumListDTO::new);
    }

    @Transactional
    public void update(Long id, AlbumUpdateDTO dto) {
        Album album = findAlbum(id);
        validateAlbum(dto, album);
        if (dto.artistId() != null) {
            Artist artist = findArtist(dto.artistId());
            album.setArtist(artist);
        }
        album.update(dto);
    }

    @Transactional
    public void delete(Long id) {
        findAlbum(id);
        albumRepository.deleteById(id);
    }

    private Artist findArtist(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));
    }

    private Album findAlbum(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found!"));
    }

    private void validateArtist(Long artistId) {
        if (artistId != null && !artistRepository.existsById(artistId))
            throw new ResourceNotFoundException("Artist not found!");
    }

    private void validateAlbum(AlbumUpdateDTO dto, Album album) {
        Artist artist = dto.artistId() != null
                ? findArtist(dto.artistId())
                : album.getArtist();

        if (albumRepository.existsByTitleIgnoreCaseAndArtistAndIdNot(dto.title(), artist, album.getId())) {
            throw new DuplicateResourceException("Album with the title " + dto.title()
                    + " from artist " + artist.getName()
                    + " already exists!");
        }
    }
}
