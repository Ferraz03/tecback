package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = service.criar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosOsUsuarios() {
        List<Usuario> usuarios = service.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable UUID id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = service.atualizar(id, usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
