package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;


    public Usuario criar(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (repository.findByCpfCnpj(usuario.getCpfCnpj()).isPresent()) {
            throw new RuntimeException("CPF/CNPJ já cadastrado");
        }


        return repository.save(usuario);
    }

    public List<Usuario> listarTodos() {

        return repository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
        usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());

        return repository.save(usuarioExistente);
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        repository.deleteById(id);
    }
}




