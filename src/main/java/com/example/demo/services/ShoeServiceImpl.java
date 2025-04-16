package com.example.demo.services;

import com.example.demo.models.Entities.Shoe;
import com.example.demo.repositories.ShoeRepository;
import org.springframework.stereotype.Service;

@Service
public class ShoeServiceImpl extends BaseServiceImpl<Shoe, Long> {

    private final ShoeRepository shoeRepository;

    public ShoeServiceImpl(ShoeRepository shoeRepository) {
        super(shoeRepository);
        this.shoeRepository = shoeRepository;
    }

    // Podés agregar métodos específicos acá si querés
}
