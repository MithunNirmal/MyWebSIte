package com.mithunnirmal.merch.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mithunnirmal.merch.enums.AlbumType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Album extends Auditable{

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    String id;

    String name;

    String coverLink;

    String primaryArtist;

    @Enumerated(EnumType.STRING)
    AlbumType type;

//    @OneToOne
//    Artist primaryArtist;

    String link;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    List<Song> songs;
}
