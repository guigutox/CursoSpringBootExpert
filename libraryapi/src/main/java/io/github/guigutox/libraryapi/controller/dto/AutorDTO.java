package io.github.guigutox.libraryapi.controller.dto;

import io.github.guigutox.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Past;


import java.time.LocalDate;

public record AutorDTO(
        @NotBlank(message = "Campo obrigatorio") @Size(min = 2, max = 100, message = "Campo fora do tamanho padrao") String nome,
        @NotNull(message = "Campo obrigatorio") @Past(message = "Nao pode ser uma data fatura") LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio") @Size(max = 50, min = 2, message = "Campo fora do tamanho padrao") String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setDataNascimento(dataNascimento);
        autor.setNacionalidade(nacionalidade);
        return autor;
    }

}
