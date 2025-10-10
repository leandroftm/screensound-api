package com.screensound.api.controller;

import com.screensound.api.dto.album.AlbumCreateDTO;
import com.screensound.api.dto.album.AlbumListDTO;
import com.screensound.api.dto.album.AlbumUpdateDTO;
import com.screensound.api.dto.artist.ArtistCreateDTO;
import com.screensound.api.dto.artist.ArtistListDTO;
import com.screensound.api.dto.artist.ArtistUpdateDTO;
import com.screensound.api.dto.music.MusicCreateDTO;
import com.screensound.api.dto.music.MusicListDTO;
import com.screensound.api.dto.music.MusicUpdateDTO;
import com.screensound.api.service.AlbumService;
import com.screensound.api.service.ArtistService;
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
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicService musicService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ArtistCreateDTO dto) {
        Long id = artistService.create(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{artistName}/albums")
    public ResponseEntity<Void> createAlbum(@RequestBody @Valid AlbumCreateDTO dto, @PathVariable String artistName) {
        Long id = albumService.create(artistName, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{artistName}/{albumTitle}/musics")
    public ResponseEntity<Void> createMusic(@RequestBody @Valid MusicCreateDTO dto, @PathVariable String artistName, @PathVariable String albumTitle) {
        Long id = musicService.create(artistName, albumTitle, dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{artistName}/albums")
    public ResponseEntity<Page<AlbumListDTO>> listAlbumsByArtist(@PageableDefault(size = 20, sort = {"title"}) Pageable pageable, @PathVariable String artistName) {
        Page<AlbumListDTO> page = albumService.listByArtist(pageable, artistName);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{artistName}/albums/musics")
    public ResponseEntity<Page<MusicListDTO>> listMusicByArtist(@PageableDefault(size = 20, sort = ("title")) Pageable pageable, @PathVariable String artistName) {
        Page<MusicListDTO> page = musicService.listByArtist(pageable, artistName);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{artistName}/albums/{albumTitle}/musics")
    public ResponseEntity<Page<MusicListDTO>> listMusicByArtistAndAlbum(@PageableDefault(size = 20, sort = ("title")) Pageable pageable,
                                                                        @PathVariable String artistName,
                                                                        @PathVariable String albumTitle) {
        Page<MusicListDTO> page = musicService.listByArtistAndAlbum(pageable, artistName, albumTitle);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<ArtistListDTO>> list(@PageableDefault(size = 20, sort = {"name"}) Pageable pageable) {
        Page<ArtistListDTO> page = artistService.list(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid ArtistUpdateDTO dto) {
        artistService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{artistName}/albums/{id}")
    public ResponseEntity<Void> updateAlbum(@PathVariable Long id, @RequestBody @Valid AlbumUpdateDTO dto) {
        albumService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{artistName}/albums/{albumTitle}/musics/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid MusicUpdateDTO dto) {
        musicService.update(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
