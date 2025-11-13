package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.StatusAssinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor

public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @Data
    public static class AssinaturaRequest {
        @NotNull
        private UUID usuarioId;
        @NotNull private UUID planoId;
    }

    @PostMapping
    public ResponseEntity<?> criarAssinatura(@Valid @RequestBody AssinaturaRequest request) {
        try {
            Assinatura assinatura = assinaturaService.criar(request.getUsuarioId(), request.getPlanoId());
            return ResponseEntity.status(HttpStatus.CREATED).body(assinatura);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (ConflitoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarAssinatura(@PathVariable UUID id) {
        try {
            Assinatura assinatura = assinaturaService.cancelar(id);
            return ResponseEntity.ok(assinatura);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        } catch (ConflitoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Assinatura>> consultarAssinaturas(
            @RequestParam(required = false) StatusAssinatura status) {

        if (status != null) {
            return ResponseEntity.ok(assinaturaService.listarPorStatus(status));
        }
        return ResponseEntity.ok().build();
    }
}
