package io.github.guigutox.libraryapi.service;

import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.model.GeneroLivro;
import io.github.guigutox.libraryapi.model.Livro;
import io.github.guigutox.libraryapi.repository.AutorRepository;
import io.github.guigutox.libraryapi.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public void atualizacaoSemAtualizar(){
        var livro = livroRepository.findById(UUID.fromString("a7f11371-0778-4e03-b46b-b2f4a8dc32b3")).orElseThrow();

        livro.setDataPublicacao(LocalDate.now());
       // livroRepository.save(livro);
    }

    @Transactional
    public void executar(){
        //salvar o autor
        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2000, 2, 2));

        autorRepository.save(autor);


        //salvar o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(2021, 10, 10));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Mario")){
            throw new RuntimeException("Erro ao salvar o autor");
        }

    }

    @Transactional
    public void salvarLivroComFoto(){
        
    }

}
