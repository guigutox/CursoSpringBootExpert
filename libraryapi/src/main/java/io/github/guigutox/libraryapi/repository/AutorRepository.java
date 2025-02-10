package io.github.guigutox.libraryapi.repository;


import io.github.guigutox.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface AutorRepository extends JpaRepository<Autor, UUID> {


}
