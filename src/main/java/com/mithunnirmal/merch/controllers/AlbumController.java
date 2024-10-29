package com.mithunnirmal.merch.controllers;

import com.mithunnirmal.merch.modelDtos.AlbumDto;
import com.mithunnirmal.merch.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/album")
@Slf4j
public class AlbumController {

    @Autowired
    private AlbumService albumService;
    @GetMapping(path = "/public/albums")
    public ResponseEntity<List<AlbumDto>> getAllAlbums() {
        List<AlbumDto> list;
        try {
            log.info("entering get albums");
            list = albumService.getAllAlbums();
            log.info("got a list " + list);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping(path = "/addAlbum")
    public ResponseEntity<String> addAlbum(@RequestBody AlbumDto albumDto) {
        //TODO: Only stores database information. media file storage needs to be implemented
        try {
            System.out.println("adskljfhaskdfha");
            albumService.addAlbum(albumDto);
        }catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        return ResponseEntity.ok("Successfully Added");
    }

    @PostMapping()
    public ResponseEntity<String> addAlbumToGDrive(@RequestBody String str) {
        try {
            albumService.addAlbumToGDrive();
        }catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
        return ResponseEntity.ok("Successfully Added");
    }

    @PostMapping("/admin/addAlbumWithGDriveLink")
    public ResponseEntity<String> addAlbumWithGoogleDriveLink (@RequestBody AlbumDto albumDto) {

        try {
            albumService.addAlbumWithGoogleDriveLink(albumDto);
        }catch (Exception e) {return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}

        return ResponseEntity.ok("Successfully added ");
    }

    @GetMapping("/public/albumsGDrive")
    public ResponseEntity<List<AlbumDto>> getAllAlbumsBasedOnGDriveId () {
        List<AlbumDto> list;
        try {
            log.info("entering get albums");
            list = albumService.getAllAlbumsBasedOnGDriveId();
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable("id") String id) {
        AlbumDto dto = null;
        try{
            dto = albumService.getAlbumById(id);
        }catch (Exception e) {return new ResponseEntity<>((HttpStatus.INTERNAL_SERVER_ERROR));}

        return ResponseEntity.ok(dto);
    }
 }
