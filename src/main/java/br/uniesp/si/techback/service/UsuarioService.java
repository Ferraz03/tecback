package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;


    public Usuario criar(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new ConflitoDeDadosException("Email já cadastrado");
        }
        if (repository.findByCpfCnpj(usuario.getCpfCnpj()).isPresent()) {
            throw new ConflitoDeDadosException("CPF/CNPJ já cadastrado");
        }


        return repository.save(usuario);
    }

    public Usuario buscarPorId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário com ID" + id + " não encontrado"));
    }

    public Usuario atualizar(UUID id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);
        usuarioExistente.setNomeCompleto(usuarioAtualizado.getNomeCompleto());
        usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());

        return repository.save(usuarioExistente);
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Usuário com ID" + id + " não encontrado");
        }
        repository.deleteById(id);
    }

    public Page<Usuario> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }
}




