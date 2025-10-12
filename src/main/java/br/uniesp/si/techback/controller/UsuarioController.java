package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService conteudoService;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = conteudoService.criar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (ConflitoDeDadosException erro) {
            Map<String, String> errado = Map.of("erro", erro.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body((Usuario) errado);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> listarTodosOsUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = conteudoService.listarPaginado(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable UUID id) {
        try {
            Usuario usuario = conteudoService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (EntidadeNaoEncontradaException erro) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario usuario = conteudoService.atualizar(id, usuarioAtualizado);
            return ResponseEntity.ok(usuario);
        } catch (EntidadeNaoEncontradaException erro) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        try {
            conteudoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException erro){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
