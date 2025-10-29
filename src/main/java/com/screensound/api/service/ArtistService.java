package com.screensound.api.service;

import com.screensound.api.dto.artist.ArtistCreateDTO;
import com.screensound.api.dto.artist.ArtistListDTO;
import com.screensound.api.dto.artist.ArtistUpdateDTO;
import com.screensound.api.entity.Artist;
import com.screensound.api.exceptions.DuplicateResourceException;
import com.screensound.api.exceptions.ResourceNotFoundException;
import com.screensound.api.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    @Transactional
    public Long create(ArtistCreateDTO dto) {
        if (repository.existsByNameIgnoreCase(dto.name())) {
            throw new ResourceNotFoundException("Artist name already created!");
        }
        Artist artist = new Artist(dto);
        repository.save(artist);
        return artist.getId();
    }


    @Transactional(readOnly = true)
    public Page<ArtistListDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(ArtistListDTO::new);
    }

    @Transactional
    public void update(Long id, ArtistUpdateDTO dto) {
        if (dto.name() == null && dto.artistType() == null)
            throw new IllegalArgumentException("At least one field must be filled to update!");

        Artist artist = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found!"));

        if(repository.existsByNameIgnoreCase(dto.name())){
            throw new DuplicateResourceException("Artist with the name " + dto.name()
                    + " already exists!");
        }
        artist.update(dto);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Artist not found!");
        }
        repository.deleteById(id);
    }
}
