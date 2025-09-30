package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // POST - Cria um usuário.
    public Usuario criar(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }
        if (repository.findByCpfCnpj(usuario.getCpfCnpj()).isPresent()) {
            throw new RuntimeException("CPF/CNPJ já cadastrado");
        }


        return repository.save(usuario);
    }

    // GET - Lista todos os usuários salvos.
    public List<Usuario> listarTodos() {

        return repository.findAll();
    }

    // GET - Busca um usuário pelo ID.
    public Usuario buscarPorId(long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // PUT - Atualiza dados de um usuário a partir de seu ID.
    public Usuario atualizar(long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
        usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());

        return repository.save(usuarioExistente);
    }

    // DELETE - Deleta um usuário através de seu ID.
    public void deletar(long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado.");
        }
        repository.deleteById(id);
    }
}




