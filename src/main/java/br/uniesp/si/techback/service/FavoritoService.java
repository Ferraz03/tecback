package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.FavoritoId;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.ConteudoRepository;
import br.uniesp.si.techback.repository.FavoritoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConteudoRepository conteudoRepository;

    public Favorito adicionar(UUID usuarioId, UUID conteudoId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado"));
        Conteudo conteudo = conteudoRepository.findById(conteudoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Conteúdo não encontrado"));

        FavoritoId favoritoId = new FavoritoId(usuarioId,conteudoId);

        if (favoritoRepository.existsById(favoritoId)){
            throw new ConflitoDeDadosException("Esse conteúdo já foi favoritado por este usuário");
        }

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setConteudo(conteudo);

        return favoritoRepository.save(favorito);
    }

    public void remover (UUID usuarioId, UUID conteudoId){
        FavoritoId favoritoId = new FavoritoId(usuarioId, conteudoId);

        if (!favoritoRepository.existsById(favoritoId)){
            throw new EntidadeNaoEncontradaException("Este favorito não existe");
        }

        favoritoRepository.deleteById(favoritoId);
    }

    public List<Favorito> listarPorUsuario(UUID usuarioId){
        if (!usuarioRepository.existsById(usuarioId)){
            throw new EntidadeNaoEncontradaException("Usuário não encontrada");
        }

        return favoritoRepository.findByUsuarioIdOrderByCriadoEmDesc(usuarioId);
    }
}
