package com.example.demo.repository;

import com.example.demo.enums.Sexo;
import com.example.demo.enums.TipoProducto;
import com.example.demo.model.Categoria;
import com.example.demo.model.Imagen;
import com.example.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {


    List<Producto> findByNombre(String nombre);

    List<Producto> findByNombreContaining(String nombreParcial);

    List<Producto> findByTipoProducto(TipoProducto tipoProducto);

    List<Producto> findBySexo(Sexo sexo);

    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria AND p.tipoProducto = :tipoProducto")
    List<Producto> findByCategoriaAndTipoProducto(Categoria categoria, TipoProducto tipoProducto);

    @Query("SELECT DISTINCT p FROM Producto p " +
            "JOIN p.detalles d " +
            "JOIN d.talle t " +
            "WHERE (:talles IS NULL OR t.talle IN :talles) " +
            "AND (:precioMin IS NULL OR d.precio.precioVenta >= :precioMin) " +
            "AND (:precioMax IS NULL OR d.precio.precioVenta <= :precioMax) " +
            "AND (:sexo IS NULL OR p.sexo = :sexo) " +
            "AND (:tipoProducto IS NULL OR p.tipoProducto = :tipoProducto)" +
            "AND (:colores IS NULL OR d.color IN :colores)" +
            "AND (:categoria IS NULL OR p.categoria = :categoria)"
    )
    List<Producto> filtrarProductos(@Param("talles") List<String> talles,
                                    @Param("precioMin") Double precioMin,
                                    @Param("precioMax") Double precioMax,
                                    @Param("sexo") Sexo sexo,
                                    @Param("tipoProducto") TipoProducto tipoProducto,
                                    @Param("colores") List<String> colores,
                                    @Param("categoria") Categoria categoria
    );
    boolean existsByNombre(String nombre);

    boolean existsByImagen(Imagen imagen);

}


