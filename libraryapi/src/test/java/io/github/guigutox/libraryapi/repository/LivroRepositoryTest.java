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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository AutorRepository;
    @Autowired
    private AutorRepository autorRepository;

    @Test
    void SalvarTeste() {
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro");
        livro.setDataPublicacao(LocalDate.of(2021, 10, 10));

       Autor autor = AutorRepository.findById(UUID.fromString("0de70675-e824-4dbd-b1a0-87e36879f59a")).orElse(null);

        livro.setAutor(autor);

        repository.save(livro);

    }


    @Test
    void salvarAutorELivroTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(2021, 10, 10));

        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 2, 2));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);

    }



   /* @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro livro Cascade");
        livro.setDataPublicacao(LocalDate.of(2021, 10, 10));

        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 2, 2));

        livro.setAutor(autor);

        repository.save(livro);

    }*/


    @Test
    void atualizarAutorDoLivro(){
        UUID id = UUID.fromString("b3901cf2-7778-414e-9f9d-df9673310bec");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("a7552cd3-4df6-49b8-a6e0-eaaffeb6df03");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(autor);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("b3901cf2-7778-414e-9f9d-df9673310bec");
        repository.deleteById(id);
    }

   /* @Test
    void deletarCascadeTest(){
        UUID id = UUID.fromString("b3901cf2-7778-414e-9f9d-df9673310bec");
        repository.deleteById(id);
    }*/

    @Test
    @Transactional //Serve para que o teste não de erro de lazy initialization
    void buscarLivroTest(){
        UUID id = UUID.fromString("53e4445e-fc74-406e-a9c5-bd045aaaccc4");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println("Titulo "+livro.getTitulo());

        System.out.println("Autor: "); //Lazy initialization
        System.out.println("Nome "+livro.getAutor().getNome()); //Lazy initialization

    }

    @Test
    void pesquisaPorTitulo(){
        var livros = repository.findByTitulo("Quarto Livro");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbn(){
        var livros = repository.findByIsbn("90833-84874");
        livros.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco(){
        var preco = BigDecimal.valueOf(150);
        var titulo = "Quarto Livro";
        var livros = repository.findByTituloAndPreco(titulo, preco.doubleValue());
        livros.forEach(System.out::println);
    }

    @Test
    void listaLivrosComQueryJPQL(){
        var resultado = repository.listarTodos();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaLivrosOrdenadosPorTituloEPreco(){
        var resultado = repository.listarTodosOrdenadosPorTituloEPorPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listaAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidos(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }
    @Test
    void listarPorGeneroPositionalParameters(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.FICCAO, "dataPublicacao");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletarPorGenero(){
        repository.deleteByGenero(GeneroLivro.FICCAO);
    }

    @Test
    void atualizarPrecoPorGenero(){
        repository.updatePrecoByGenero(BigDecimal.valueOf(200).doubleValue(), GeneroLivro.CIENCIA);
    }

}