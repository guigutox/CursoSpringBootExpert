package io.github.guigutox.libraryapi.controller;


import io.github.guigutox.libraryapi.controller.dto.AutorDTO;
import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
// http://host:8080/autores

public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
            Autor autorEntidade = autor.mapearParaAutor();
            service.salvar(autorEntidade);

        //http://host:8080/autores/{id}
       URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

            return ResponseEntity.created(location).build();

    }

}
