package com.screensound.api.service;

import com.screensound.api.dto.music.MusicCreateDTO;
import com.screensound.api.dto.music.MusicListDTO;
import com.screensound.api.dto.music.MusicUpdateDTO;
import com.screensound.api.entity.Album;
import com.screensound.api.entity.Artist;
import com.screensound.api.entity.Music;
import com.screensound.api.exceptions.DuplicateResourceException;
import com.screensound.api.exceptions.ResourceNotFoundException;
import com.screensound.api.repository.AlbumRepository;
import com.screensound.api.repository.ArtistRepository;
import com.screensound.api.repository.MusicRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private AlbumRepository albumRepository;

    @Transactional
    public Long create(Long artistId, Long albumId, MusicCreateDTO dto) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found!"));


        Music music = new Music(dto.title(), artist, album, dto.length());
        musicRepository.save(music);
        return music.getId();
    }

    @Transactional(readOnly = true)
    public Page<MusicListDTO> list(Pageable pageable) {
        return musicRepository.findAll(pageable).map(MusicListDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<MusicListDTO> listByArtist(Long artistId, Pageable pageable) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));

        return musicRepository.findAllByArtist(artist, pageable).map(MusicListDTO::new);
    }

    public Page<MusicListDTO> listByArtistAndAlbum(Long artistId, Long albumId, Pageable pageable) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found!"));

        return musicRepository.findAllByArtistAndAlbum(artist, album, pageable).map(MusicListDTO::new);
    }

    @Transactional
    public void update(Long id, @Valid MusicUpdateDTO dto) {
        if (dto.title() == null)
            throw new IllegalArgumentException("Title field must be filled for update!");

        Music music = musicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Music not found!"));

        if (musicRepository.existsByTitleIgnoreCaseAndArtistAndIdNot(dto.title(), music.getArtist(), id)) {
            throw new DuplicateResourceException("Music with the title " + dto.title()
                    + " from artist " + music.getArtist().getName()
                    + " already exists!");
        }

        music.update(dto);

    }

    @Transactional
    public void delete(Long id) {
        if (!musicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Music not found!");
        }
        musicRepository.deleteById(id);
    }
}

