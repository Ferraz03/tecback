package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ConteudoRepository extends JpaRepository<Conteudo, UUID> , JpaSpecificationExecutor<Conteudo> {

    boolean existsByTituloIgnoreCaseAndAnoAndTipo(String titulo, Integer ano, Tipo tipo);

    boolean existsByTituloIgnoreCaseAndAnoAndTipoAndIdNot(String titulo, Integer ano, Tipo tipo, UUID id);

}
