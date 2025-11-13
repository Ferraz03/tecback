package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.ConflitoDeDadosException;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.StatusAssinatura;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import br.uniesp.si.techback.repository.PlanoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PlanoRepository planoRepository;

    public Assinatura criar(UUID usuarioId, UUID planoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado."));
        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Plano não encontrado."));

        assinaturaRepository.findByUsuarioIdAndStatus(usuarioId, StatusAssinatura.ATIVA)
                .ifPresent(a -> {
                    throw new ConflitoDeDadosException("O usuário já possui uma assinatura ativa.");
                });

        Assinatura novaAssinatura = new Assinatura();
        novaAssinatura.setUsuario(usuario);
        novaAssinatura.setPlano(plano);
        novaAssinatura.setStatus(StatusAssinatura.ATIVA);
        return assinaturaRepository.save(novaAssinatura);
    }

    public Assinatura cancelar(UUID assinaturaId) {
        Assinatura assinatura = assinaturaRepository.findById(assinaturaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Assinatura não encontrada."));

        if (assinatura.getStatus() == StatusAssinatura.CANCELADA) {
            throw new ConflitoDeDadosException("Esta assinatura já está cancelada.");
        }

        assinatura.setStatus(StatusAssinatura.CANCELADA);
        assinatura.setCanceladaEm(LocalDateTime.now());

        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listarPorStatus(StatusAssinatura status) {
        return assinaturaRepository.findByStatus(status);
    }
}
