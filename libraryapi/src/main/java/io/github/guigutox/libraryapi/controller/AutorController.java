package io.github.guigutox.libraryapi.controller;


import io.github.guigutox.libraryapi.controller.dto.AutorDTO;
import io.github.guigutox.libraryapi.controller.dto.ErroResposta;
import io.github.guigutox.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.guigutox.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.guigutox.libraryapi.model.Autor;
import io.github.guigutox.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ObjectStreamClass;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
// http://host:8080/autores
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;


    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autor){
    try{
        Autor autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        //http://host:8080/autores/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }catch (RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }


    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        }catch (OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);

        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
             @RequestParam(value = "nome", required = false) String nome,
             @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultado = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream().map(autor -> new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto){
    try{
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();

    }catch (RegistroDuplicadoException e){
        var erroDTO = ErroResposta.conflito(e.getMessage());
        return ResponseEntity.status(erroDTO.status()).body(erroDTO);
    }

    }


}
