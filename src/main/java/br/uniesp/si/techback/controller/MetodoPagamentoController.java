package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios/{usuarioId}/metodos-pagamento")
@RequiredArgsConstructor

public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    @PostMapping
    public ResponseEntity<?> criarMetodoPagamento(@PathVariable UUID usuarioId, @Valid @RequestBody MetodoPagamento metodoPagamento) {
        try {
            MetodoPagamento novoMetodo = metodoPagamentoService.criar(usuarioId, metodoPagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoMetodo);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> listarMetodosPorUsuario(@PathVariable UUID usuarioId) {
        try {
            List<MetodoPagamento> metodos = metodoPagamentoService.listarPorUsuario(usuarioId);
            return ResponseEntity.ok(metodos);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{metodoPagamentoId}")
    public ResponseEntity<Void> removerMetodoPagamento(@PathVariable UUID usuarioId, @PathVariable UUID metodoPagamentoId) {
        try {
            metodoPagamentoService.remover(usuarioId, metodoPagamentoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
