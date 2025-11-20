package com.screensound.api.controller;

import com.screensound.api.dto.album.AlbumListDTO;
import com.screensound.api.dto.album.AlbumCreateDTO;
import com.screensound.api.dto.album.AlbumUpdateDTO;
import com.screensound.api.service.AlbumService;
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
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AlbumCreateDTO dto) {
        Long id = service.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<AlbumListDTO>> list(Long artistId, @PageableDefault(size = 20, sort = {"title"}) Pageable pageable) {
        Page<AlbumListDTO> page = service.list(artistId, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid AlbumUpdateDTO dto) {
        service.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
