package com.mithunnirmal.merch.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Song extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column
    String name;
    @Column
    String coverLink;
    @Column
    String link;
    @Column
    String artist;

//    Artist primaryArtist;

//    @OneToMany(mappedBy = "artist")
//    Set<Artist> artists;// = new HashSet<>();

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id", referencedColumnName="id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ALBUM"))
    Album album;

}
