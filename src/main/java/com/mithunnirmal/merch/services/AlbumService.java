package com.mithunnirmal.merch.services;

import com.mithunnirmal.merch.entities.Album;
import com.mithunnirmal.merch.entities.Song;
import com.mithunnirmal.merch.modelDtos.AlbumDto;
import com.mithunnirmal.merch.modelDtos.SongDto;
import com.mithunnirmal.merch.repositories.AlbumRepository;
import com.mithunnirmal.merch.repositories.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mithunnirmal.merch.utils.AlbumUtils.getFileIdFromGDriveLink;
import static com.mithunnirmal.merch.utils.Final.G_DRIVE_THUMBNAIL;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private SongRepository songRepository;


    public List<AlbumDto> getAllAlbums() {
        Optional<List<Album>> albums = Optional.of(albumRepository.findAll());
        System.out.println("jhgvhgcuhvih");
        if(!albums.isEmpty()) {
//            List<AlbumDto> albumDtos = albums.get()
//                    .stream()
//                    .map(album -> modelMapper.map(album, AlbumDto.class))
//                    .collect(Collectors.toList());

            List<AlbumDto> dtos = albums.get()
                    .stream()
                    .map(album -> {
                        return new AlbumDto().builder()
                                .id(album.getId())
                                .name(album.getName())
                                .link(album.getLink())
                                .primaryArtist(album.getPrimaryArtist())
                                .coverLink(album.getCoverLink())
                                .songs(album.getSongs().stream().map(song -> {
                                    return new SongDto().builder()
                                            .id(song.getId())
                                            .name(song.getName())
                                            .coverLink(song.getCoverLink())
                                            .artist(song.getArtist())
                                            .link(song.getLink())
                                            .build();
                                }).collect(Collectors.toList()))
                                .build();
                    })
                    .collect(Collectors.toList());



            return dtos;
        } else{
            System.out.println("Albums empty");
        }
        return null;
    }

    public String addAlbum(AlbumDto albumDto) {
        System.out.println("Entering service");
        Album album = modelMapper.map(albumDto, Album.class);

//        album = Album.builder()
//                .name(albumDto.getName())
//                .link(albumDto.getLink())
//                .coverLink(albumDto.getCoverLink())
//                .primaryArtist(albumDto.getPrimaryArtist())
//                .songs(albumDto.getSongs())
//                .build();

        System.out.println("model mapping done\n" + album);
        List<Song> songs = album.getSongs();
        album.setSongs(null);
        Album savedAlbum = albumRepository.save(album);
        System.out.println("added successfully \n" + savedAlbum);

        songs.forEach(song -> song.setAlbum(album));
        songRepository.saveAll(songs);
        return "added";
    }

    public String addAlbumWithGoogleDriveLink(AlbumDto albumDto) {
        Album album = Album.builder()
                .name(albumDto.getName())
                .link(getFileIdFromGDriveLink(albumDto.getLink()))
                .coverLink(getFileIdFromGDriveLink(albumDto.getCoverLink()))
                .primaryArtist(albumDto.getPrimaryArtist())
                .build();
        albumRepository.save(album);

        List<Song> songs =  albumDto.getSongs().stream().map(dto -> {
                    return new Song().builder()
                            .name(dto.getName())
                            .coverLink(    //Getting ID from the link
                                    getFileIdFromGDriveLink(dto.getCoverLink()==null?
                                                                album.getCoverLink()
                                                            :   dto.getCoverLink()))
                            .link(getFileIdFromGDriveLink(dto.getLink()))//Getting ID from the link
                            .artist(dto.getArtist())
                            .album(album)
                            .build();
                }).collect(Collectors.toList());

        songRepository.saveAll(songs);
        return "Success";
    }

    public List<AlbumDto> getAllAlbumsBasedOnGDriveId() {
        List<Album> albums = albumRepository.findAll();

        List<AlbumDto> albumsDto = albums.stream().map(album -> {
                    return new AlbumDto().builder()
                            .id(album.getId())
                            .name(album.getName())
                            .primaryArtist(album.getPrimaryArtist())
                            .coverLink(G_DRIVE_THUMBNAIL + album.getCoverLink())
                            .songs(album.getSongs().stream().map(song -> {
                                return new SongDto().builder()
                                        .name(song.getName())
                                        .id(song.getId())
                                        .artist(song.getArtist())
                                        .coverLink(G_DRIVE_THUMBNAIL + song.getCoverLink())
                                      //  .link()
                                        .build();
                            }).collect(Collectors.toList()))
                            .build();
                    }).collect(Collectors.toList());

        return albumsDto;
    }
 }
