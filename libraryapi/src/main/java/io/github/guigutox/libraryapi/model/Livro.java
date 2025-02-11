package io.github.guigutox.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Data //Tras metodos getters, setters, to string, equalas e hashcode, required args constructor de uma vez so
@ToString(exclude = "autor") //Serve para que o autor não seja impresso no toString
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING) //A String tem que ser um dos valores do ENUM GeneroLivro
    @Column(name= "genero", length = 30, nullable = false)
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2) // Removed precision attribute
    private BigDecimal preco;

    //Entidade atual TO Entidade referenciada
    @ManyToOne(
          //  cascade = CascadeType.ALL // CascadeType.ALL serve para que quando for salvar um livro, ele salve o autor tambem
        fetch = FetchType.LAZY //LAZY= ele buscará apenas os dados do livro e não trará do autor EAGER= serve para que quando for buscar um livro, ele busque o autor tambem
    )

    @JoinColumn(name = "id_autor")
    private Autor autor;



}
