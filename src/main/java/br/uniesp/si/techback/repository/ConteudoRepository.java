package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.model.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ConteudoRepository extends JpaRepository<Conteudo, UUID> , JpaSpecificationExecutor<Conteudo> {

    boolean existsByTituloIgnoreCaseAndAnoAndTipo(String titulo, Integer ano, Tipo tipo);

    boolean existsByTituloIgnoreCaseAndAnoAndTipoAndIdNot(String titulo, Integer ano, Tipo tipo, UUID id);

    @Query("SELECT c FROM Conteudo c ORDER BY c.titulo ASC")
    List<Conteudo> listarOrdenadoPorTitulo();

    List<Conteudo> findByGeneroOrderByTituloAsc(Genero genero);

    @Query("SELECT c FROM Conteudo c ORDER BY c.relevancia DESC")
    List<Conteudo> findTopByRelevancia(Pageable pageable);

    @Query("SELECT c FROM Conteudo c WHERE c.ano > :ano ORDER BY c.ano ASC")
    List<Conteudo> findConteudosLancadosDepoisDe(@Param("ano") Integer ano);

    @Query("SELECT c FROM Conteudo c WHERE c.trailerUrl IS NOT NULL ORDER BY c.titulo ASC")
    List<Conteudo> findConteudosComTrailer();

    @Query("SELECT c FROM Conteudo c " +
            "WHERE LOWER(c.titulo) LIKE LOWER(CONCAT('%', :palavra, '%')) " +
            "OR LOWER(c.sinopse) LIKE LOWER(CONCAT('%', :palavra, '%')) " +
            "ORDER BY c.relevancia DESC")
    List<Conteudo> buscarPorPalavraChave(@Param("palavra") String palavra);

}
