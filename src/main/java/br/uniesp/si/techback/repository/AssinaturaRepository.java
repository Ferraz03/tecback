package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.StatusAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssinaturaRepository extends JpaRepository<Assinatura , UUID> {

    Optional<Assinatura> findByUsuarioIdAndStatus(UUID usuarioId, StatusAssinatura status);

    List<Assinatura> findByStatus(StatusAssinatura status);

}
