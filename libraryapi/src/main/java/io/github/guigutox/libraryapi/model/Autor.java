package io.github.guigutox.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="autor", schema = "public")
@Data
@ToString(exclude = "livros")
public class Autor {

    @Deprecated
    public Autor() {
        //Necessario construtor vazio para o Framework
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.UUID )
    private UUID id;

    @Column(name="nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name="nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @OneToMany(mappedBy = "autor") //Serve para carregar uma lista com todos os livros do autor
    @Transient
    private List<Livro> livros;

}
