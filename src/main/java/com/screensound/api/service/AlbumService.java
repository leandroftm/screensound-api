package com.screensound.api.service;

import com.screensound.api.dto.album.AlbumListDTO;
import com.screensound.api.dto.album.AlbumCreateDTO;
import com.screensound.api.dto.album.AlbumUpdateDTO;
import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import com.screensound.api.exceptions.ResourceNotFoundException;
import com.screensound.api.repository.AlbumRepository;
import com.screensound.api.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ArtistRepository artistRepository;


    @Transactional
    public Long create(Long artistId, AlbumCreateDTO dto) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));

        Album album = new Album(dto.title(), artist, dto.releaseDate());
        albumRepository.save(album);

        return album.getId();
    }

    @Transactional(readOnly = true)
    public Page<AlbumListDTO> list(Pageable pageable) {
        return albumRepository.findAll(pageable).map(AlbumListDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<AlbumListDTO> listByArtist(Long artistId, Pageable pageable) {

    Artist artist = artistRepository.findById(artistId)
            .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));

        return albumRepository.findAllByArtist(artist, pageable).map(AlbumListDTO::new);
    }

    @Transactional
    public void update(Long id, AlbumUpdateDTO dto) {
        if (dto.title() == null)
            throw new IllegalArgumentException("Title field must be filled for update!");

        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found!"));

        if(albumRepository.existsByTitleIgnoreCaseAndArtistAndIdNot(dto.title(), album.getArtist(), id)){
            throw new IllegalArgumentException("Album with the title " + dto.title()
                    + " from artist " + album.getArtist().getName()
                    + " already exists!");
        }

        album.update(dto);
    }

    @Transactional
    public void delete(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new EntityNotFoundException("Album not found!");
        }
        albumRepository.deleteById(id);
    }
}
