package com.mithunnirmal.merch.repositories;

import com.mithunnirmal.merch.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, String> {
}
