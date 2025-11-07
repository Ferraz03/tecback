package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.service.FavoritoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping ("/usuarios/{usuarioId}/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    @Data
    public static class FavoritoRequest{
        private UUID conteudoId;
    }

    @PostMapping
    public ResponseEntity<?> adicionarFavorito(@PathVariable UUID usuarioId, @RequestBody FavoritoRequest request) {
        try {
            Favorito favorito = favoritoService.adicionar(usuarioId, request.getConteudoId());
            return ResponseEntity.status(HttpStatus.CREATED).body(favorito);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (ConflitoDeDadosException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{conteudoId}")
    public ResponseEntity<Void> removerFavorito(@PathVariable UUID usuarioId, @PathVariable UUID conteudoId) {
        try {
            favoritoService.remover(usuarioId, conteudoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> listarFavoritos(@PathVariable UUID usuarioId) {
        try {
            List<Favorito> favoritos = favoritoService.listarPorUsuario(usuarioId);
            return ResponseEntity.ok(favoritos);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }
}
