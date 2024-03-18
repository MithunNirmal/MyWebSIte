package com.mithunnirmal.merch.modelDtos;

import com.mithunnirmal.merch.entities.Artist;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {
    String id;
    String name;
    String coverLink;
    String link;
    String artist;
//    Set<Artist> artists = new HashSet<>();
}
