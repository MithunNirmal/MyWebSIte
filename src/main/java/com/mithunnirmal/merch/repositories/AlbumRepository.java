package com.mithunnirmal.merch.repositories;

import com.mithunnirmal.merch.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
    Optional<Album> findByName(String name);
}
