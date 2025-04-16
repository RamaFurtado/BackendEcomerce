package com.example.demo.repositories;

import com.example.demo.models.Entities.Shoe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoeRepository extends BaseRepository<Shoe, Long> {
}
