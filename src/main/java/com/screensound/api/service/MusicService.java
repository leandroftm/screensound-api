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
import com.screensound.api.spec.MusicSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Long create(MusicCreateDTO dto) {
        Artist artist = findArtist(dto.artistId());
        Album album = findAlbum(dto.albumId());
        Music music = new Music(dto.title(), artist, album, dto.length());
        musicRepository.save(music);
        return music.getId();
    }

    public Page<MusicListDTO> list(Long artistId, Long albumId, Pageable pageable) {
        validateArtist(artistId);
        validateAlbum(albumId);

        Specification<Music> spec = Specification.allOf(
                MusicSpecs.byArtistId(artistId),
                MusicSpecs.byAlbumId(albumId)
        );
        return musicRepository.findAll(spec, pageable)
                .map(MusicListDTO::new);
    }

    @Transactional
    public void update(Long id, MusicUpdateDTO dto) {
        Music music = findMusic(id);
        validateMusic(dto, music);
        if (dto.artistId() != null) {
            Artist artist = findArtist(dto.artistId());
            music.setArtist(artist);
        }
        if (dto.albumId() != null) {
            Album album = findAlbum(dto.albumId());
            music.setAlbum(album);
        }
        music.update(dto);
    }

    public void delete(Long id) {
        findMusic(id);
        musicRepository.deleteById(id);
    }

    private Artist findArtist(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));
    }

    private Album findAlbum(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found!"));
    }

    private Music findMusic(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(() ->
                new ResourceNotFoundException("Music not found!"));
    }

    private void validateArtist(Long artistId) {
        if (artistId != null && !artistRepository.existsById(artistId))
            throw new ResourceNotFoundException("Artist not found!");
    }

    private void validateAlbum(Long albumId) {
        if (albumId != null && !albumRepository.existsById(albumId))
            throw new ResourceNotFoundException("Album not found!");
    }

    private void validateMusic(MusicUpdateDTO dto, Music music) {
        Artist artist = dto.artistId() != null
                ? findArtist(dto.artistId())
                : music.getArtist();
        Album album = dto.albumId() != null
                ? findAlbum(dto.albumId())
                : music.getAlbum();
        if (musicRepository.existsByTitleIgnoreCaseAndArtistAndIdNot(dto.title(), artist, music.getId())) {
            throw new DuplicateResourceException("Music " + dto.title()
                    + " from Artist " + artist.getName()
                    + " from Album " + album.getTitle()
                    + " already exists!");
        }
    }
}

