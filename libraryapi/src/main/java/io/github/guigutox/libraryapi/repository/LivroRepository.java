package io.github.guigutox.libraryapi.repository;

import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.model.GeneroLivro;
import io.github.guigutox.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/*
   *@see LivroRepositoryTest
*/

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query Methods
    //Select * from livro where id_autor = id
    //List<Livro> findByAutor(String titulo);

    boolean existsByAutor (Autor autor);

    List<Livro> findByTitulo(String titulo);
    List<Livro> findByIsbn(String isbn);
    List<Livro> findByTituloAndPreco(String titulo, Double preco);
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    //JPQL -> Referencia as entidades e as propriedades
    //select l from Livro as l order by l.titulo
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodos();

    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloEPorPreco();


// select autor from livro join autor on livro.autor_id = autor.id
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.titulo from Livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();


    @Query("""
       select l.genero
         from Livro l
         where l.autor.nacionalidade = 'Brasileiro'
    """)
    List<String> listarGenerosAutoresBrasileiros();

    //named parameters -> :nomeParametro
    @Query("select l from Livro l where l.genero = :genero order by :parametro")
    List<Livro> findByGenero( @Param("genero") GeneroLivro genero, @Param("parametro") String parametro);

    //positional parameters -> ?1, ?2
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro genero, String parametro);

    //delete from livro where genero = genero
    @Modifying
    @Transactional
    @Query("delete from Livro l where l.genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    //update livro set preco = preco where genero = genero
    @Modifying
    @Transactional
    @Query("update Livro l set l.preco = ?1 where l.genero = ?2")
    void updatePrecoByGenero(Double preco, GeneroLivro genero);
}
