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

@RestController
@RequestMapping("/musics")
public class MusicController {

    @Autowired
    private MusicService service;

    //create movido para artist

    @GetMapping
    public ResponseEntity<Page<MusicListDTO>> list(@PageableDefault(size = 30, sort = {"title"}) Pageable pageable) {
        Page<MusicListDTO> page = service.list(pageable);
        return ResponseEntity.ok(page);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
