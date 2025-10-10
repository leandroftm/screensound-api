package com.screensound.api.controller;

import com.screensound.api.dto.album.AlbumListDTO;
import com.screensound.api.dto.album.AlbumCreateDTO;
import com.screensound.api.dto.album.AlbumUpdateDTO;
import com.screensound.api.entity.Album;
import com.screensound.api.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService service;

    //create movido para artist

    @GetMapping
    public ResponseEntity<Page<AlbumListDTO>> list(@PageableDefault(size = 20, sort = {"title"}) Pageable pageable) {
        Page<AlbumListDTO> page = service.list(pageable);
        return ResponseEntity.ok(page);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
