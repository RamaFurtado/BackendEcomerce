package com.example.demo.repository;

import com.example.demo.model.Talle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TalleRepository extends JpaRepository<Talle, Long> {

    Optional<Talle> findByNumero(String numero);

    List<Talle> findByNumeroContaining(String numeroParcial);

    boolean existsByNumero(String numero);
}