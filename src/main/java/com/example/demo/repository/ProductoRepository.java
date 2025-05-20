package com.example.demo.repository;

import com.example.demo.enums.Categoria;
import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import com.example.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByNombre(String nombre);

    List<Producto> findByNombreContaining(String nombreParcial);

    List<Producto> findByTipoProducto(TipoProducto tipoProducto);

    List<Producto> findBySexo(Sexo sexo);

    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria AND p.tipoProducto = :tipoProducto")
    List<Producto> findByCategoriaAndTipoProducto(Categoria categoria, TipoProducto tipoProducto);

    boolean existsByNombre(String nombre);
}
