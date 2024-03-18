package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.entities.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDto {
    String id;
    String name;
    String coverLink;
    String primaryArtist;
    String link;
    List<SongDto> songs;
}
