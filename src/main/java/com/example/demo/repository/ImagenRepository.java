package com.example.demo.repository;

import com.example.demo.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {

    Optional<Imagen> findByUrl(String url);

    List<Imagen> findByUrlContaining(String urlParcial);

    boolean existsByUrl(String url);
}