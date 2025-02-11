package io.github.guigutox.libraryapi.service;

import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.repository.AutorRepository;
import io.github.guigutox.libraryapi.repository.LivroRepository;
import io.github.guigutox.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;



    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("O id do autor não pode ser nulo");

        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivros(autor)){
            throw new IllegalArgumentException("Não é permitido excluir autores que tem livros cadastrados! O autor possui livros cadastrados!");
        }

        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if(nome != null){
            return repository.findByNome(nome);
        } else if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        } else {
            return repository.findAll();
        }
    }

    public boolean possuiLivros(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
