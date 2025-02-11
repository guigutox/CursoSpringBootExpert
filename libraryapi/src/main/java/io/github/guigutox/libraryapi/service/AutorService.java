package io.github.guigutox.libraryapi.service;

import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }


    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public void atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("O id do autor não pode ser nulo");

        }
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
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

}
