package io.github.guigutox.libraryapi.controller.dto;

import io.github.guigutox.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Campo obrigatorio") String nome,
        @NotNull(message = "Campo obrigatorio") LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio") String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }

}
