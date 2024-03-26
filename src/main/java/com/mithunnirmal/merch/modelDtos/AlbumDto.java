package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.entities.Song;
import com.mithunnirmal.merch.enums.AlbumType;
import com.mithunnirmal.merch.enums.ProductType;
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
    AlbumType type;
    String link;
    List<SongDto> songs;
    ProductType productType;
}
