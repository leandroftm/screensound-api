package com.screensound.api.controller;

import com.screensound.api.dto.music.MusicCreateDTO;
import com.screensound.api.dto.music.MusicListDTO;
import com.screensound.api.dto.music.MusicUpdateDTO;
import com.screensound.api.service.MusicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/musics")
public class MusicController {

    @Autowired
    private MusicService service;

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestParam Long artistId,
            @RequestParam Long albumId,
            @RequestBody @Valid MusicCreateDTO dto) {

        Long id = service.create(artistId, albumId, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<MusicListDTO>> list(@RequestParam(required = false) Long artistId, @RequestParam(required = false) Long albumId, @PageableDefault(size = 30, sort = {"title"}) Pageable pageable) {
        Page<MusicListDTO> page;
        if (artistId != null && albumId != null) {
            page = service.listByArtistAndAlbum(artistId, albumId, pageable);
        } else if (artistId != null) {
            page = service.listByArtist(artistId, pageable);
        } else {
            page = service.list(pageable);
        }
        return ResponseEntity.ok(page);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid MusicUpdateDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
