package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios/{usuarioId}/metodos-pagamento")
@RequiredArgsConstructor
public class MetodoPagamentoController {

    private final MetodoPagamentoService metodoPagamentoService;

    // DTO para a requisição de criação
    @Data
    public static class MetodoPagamentoRequest {
        @NotBlank(message = "A bandeira é obrigatória.")
        private String bandeira;
        @NotBlank(message = "Os últimos 4 dígitos são obrigatórios.")
        @Size(min = 4, max = 4)
        private String ultimos4;
        @NotNull @Min(1) @Max(12)
        private Integer mesExp;
        @NotNull @Min(2024)
        private Integer anoExp;
        @NotBlank
        private String nomePortador;
        @NotBlank
        private String tokenGateway;
    }

    @PostMapping
    public ResponseEntity<MetodoPagamento> criarMetodoPagamento(
            @PathVariable UUID usuarioId,
            @Valid @RequestBody MetodoPagamentoRequest request) { // Recebe o DTO

        MetodoPagamento novoMetodo = metodoPagamentoService.criar(usuarioId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMetodo);
    }

    @GetMapping
    public ResponseEntity<List<MetodoPagamento>> listarMetodosPorUsuario(@PathVariable UUID usuarioId) {
        List<MetodoPagamento> metodos = metodoPagamentoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(metodos);
    }

    @DeleteMapping("/{metodoPagamentoId}")
    public ResponseEntity<Void> removerMetodoPagamento(@PathVariable UUID usuarioId, @PathVariable UUID metodoPagamentoId) {
        metodoPagamentoService.remover(usuarioId, metodoPagamentoId);
        return ResponseEntity.noContent().build();
    }
}