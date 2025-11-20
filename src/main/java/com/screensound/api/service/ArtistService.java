package com.screensound.api.service;

import com.screensound.api.dto.artist.ArtistCreateDTO;
import com.screensound.api.dto.artist.ArtistListDTO;
import com.screensound.api.dto.artist.ArtistUpdateDTO;
import com.screensound.api.entity.Artist;
import com.screensound.api.exceptions.DuplicateResourceException;
import com.screensound.api.exceptions.ResourceNotFoundException;
import com.screensound.api.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Transactional
    public Long create(ArtistCreateDTO dto) {
        if (artistRepository.existsByNameIgnoreCase(dto.name())) {
            throw new DuplicateResourceException("Artist name already created!");
        }
        Artist artist = new Artist(dto.name(), dto.genre(), dto.artistType());
        artistRepository.save(artist);
        return artist.getId();
    }

    public Page<ArtistListDTO> list(Pageable pageable) {
        return artistRepository.findAll(pageable)
                .map(ArtistListDTO::new);
    }

    @Transactional
    public void update(Long id, ArtistUpdateDTO dto) {
        validateArtist(dto.name(), id);
        Artist artist = findArtist(id);
        artist.update(dto);
    }

    public void delete(Long id) {
        findArtist(id);
        artistRepository.deleteById(id);
    }

    private Artist findArtist(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found!"));
    }

    private void validateArtist(String artistName, Long artistId) {
        if (artistRepository.existsByNameIgnoreCaseAndIdNot(artistName, artistId)) {
            throw new DuplicateResourceException("Artist " + artistName + " already exists!");
        }
    }
}
