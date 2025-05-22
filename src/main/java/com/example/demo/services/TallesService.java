package com.example.demo.services;

import com.example.demo.model.Talles;
import com.example.demo.repository.TallesRepository;
import com.example.demo.services.generics.GenericServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TallesService extends GenericServiceImpl<Talles, Long> {

    private final TallesRepository tallesRepository;

    public Optional<Talles> buscarPorTalle(String talle) {
        return tallesRepository.findByTalle(talle);
    }

}
