package com.example.demo.repository;

import com.example.demo.model.Talles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TallesRepository extends JpaRepository<Talles, Long> {

    Optional<Talles> findByTalle(String talle);


}