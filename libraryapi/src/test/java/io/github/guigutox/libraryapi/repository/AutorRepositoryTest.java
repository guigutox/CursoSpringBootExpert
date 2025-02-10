package io.github.guigutox.libraryapi.repository;

import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.model.GeneroLivro;
import io.github.guigutox.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTeste(){
        var autor = new Autor();
        autor.setNome("Jose");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1990, 2, 2));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest() {
        var id = UUID.fromString("66818d9c-6067-4728-a7cd-3b7af770aaca");
        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            var autor = possivelAutor.get();
            System.out.println("Dad0s do autor: " + autor);
            autor.setDataNascimento(LocalDate.of(1990, 2, 2));
            repository.save(autor);

        }
    }

    @Test
    public void listarTest(){
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores "+repository.count());
    }

   /* @Test
    public void deleteTest(){
        var id = UUID.fromString("66818d9c-6067-4728-a7cd-3b7af770aaca");
        repository.deleteById(id);
   }*/

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 2, 2));

        Livro livro = new Livro();
        livro.setIsbn("90833-84874");
        livro.setPreco(BigDecimal.valueOf(150));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Quarto Livro");
        livro.setDataPublicacao(LocalDate.of(2024, 10, 10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("90811-84874");
        livro2.setPreco(BigDecimal.valueOf(120));
        livro2.setGenero(GeneroLivro.CIENCIA);
        livro2.setTitulo("Quinto Livro");
        livro2.setDataPublicacao(LocalDate.of(2024, 11, 13));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());



    }


    /*@Test
   // @Transactional
    void listarLivrosAutor(){
        var id = UUID.fromString("f23406cd-836a-4dfb-bb2f-c82b58bb2482");
        var autor = repository.findById(id).get();

       List<Livro> livros = livroRepository.findByAutor(autor.getNome());
       autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);



    }*/


}
