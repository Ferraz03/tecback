package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.service.FavoritoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/usuarios/{usuarioId}/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @Data
    public static class FavoritoRequest {
        @NotNull(message = "O ID do conteúdo é obrigatório.")
        private UUID conteudoId;
    }

    @PostMapping
    public ResponseEntity<Favorito> adicionarFavorito(
                                                       @PathVariable UUID usuarioId,
                                                       @Valid @RequestBody FavoritoRequest request) {

        Favorito favorito = favoritoService.adicionar(usuarioId, request.getConteudoId());
        return ResponseEntity.status(HttpStatus.CREATED).body(favorito);
    }

    @DeleteMapping("/{conteudoId}")
    public ResponseEntity<Void> removerFavorito(@PathVariable UUID usuarioId, @PathVariable UUID conteudoId) {
        favoritoService.remover(usuarioId, conteudoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Favorito>> listarFavoritos(@PathVariable UUID usuarioId) {
        List<Favorito> favoritos = favoritoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(favoritos);
    }
}