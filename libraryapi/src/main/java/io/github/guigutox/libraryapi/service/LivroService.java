package io.github.guigutox.libraryapi.service;

import io.github.guigutox.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class LivroService {
    private final LivroRepository repository;


}
