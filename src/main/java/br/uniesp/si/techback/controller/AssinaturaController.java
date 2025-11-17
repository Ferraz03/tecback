package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.StatusAssinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; // Importe o Page
import org.springframework.data.domain.Pageable; // Importe o Pageable
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assinaturas") // <-- REFINAMENTO 2: Rota versionada
@RequiredArgsConstructor
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @Data
    public static class AssinaturaRequest {
        @NotNull private UUID usuarioId;
        @NotNull private UUID planoId;
    }

    @PostMapping
    public ResponseEntity<Assinatura> criarAssinatura(@Valid @RequestBody AssinaturaRequest request) {
        Assinatura assinatura = assinaturaService.criar(request.getUsuarioId(), request.getPlanoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(assinatura);
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Assinatura> cancelarAssinatura(@PathVariable UUID id) {
        Assinatura assinatura = assinaturaService.cancelar(id);
        return ResponseEntity.ok(assinatura);
    }

    @GetMapping
    public ResponseEntity<?> consultarAssinaturas(
            @RequestParam(required = false) StatusAssinatura status,
            Pageable pageable) {

        if (status != null) {
            return ResponseEntity.ok(assinaturaService.listarPorStatus(status));
        }

        Page<Assinatura> assinaturasPaginadas = assinaturaService.listarPaginado(pageable);
        return ResponseEntity.ok(assinaturasPaginadas);
    }
}