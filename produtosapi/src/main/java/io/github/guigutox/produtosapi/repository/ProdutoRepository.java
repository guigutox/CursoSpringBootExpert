package io.github.guigutox.produtosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.guigutox.produtosapi.model.Produto;
import java.util.List;

public interface ProdutoRepository extends JpaRepository <Produto, String> {

    List<Produto> findByNome(String nome);

}
