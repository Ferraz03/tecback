package br.uniesp.si.techback.service;

import br.uniesp.si.techback.controller.MetodoPagamentoController;
import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final UsuarioRepository usuarioRepository;

    public MetodoPagamento criar(UUID usuarioId, MetodoPagamentoController.MetodoPagamentoRequest request) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado."));

        MetodoPagamento novoMetodo = new MetodoPagamento();
        novoMetodo.setBandeira(request.getBandeira());
        novoMetodo.setUltimos4(request.getUltimos4());
        novoMetodo.setMesExp(request.getMesExp());
        novoMetodo.setAnoExp(request.getAnoExp());
        novoMetodo.setNomePortador(request.getNomePortador());
        novoMetodo.setTokenGateway(request.getTokenGateway());

        // Associa ao usuário correto
        novoMetodo.setUsuario(usuario);

        return metodoPagamentoRepository.save(novoMetodo);
    }

    public List<MetodoPagamento> listarPorUsuario(UUID usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        }
        return metodoPagamentoRepository.findByUsuarioId(usuarioId);
    }

    public void remover(UUID usuarioId, UUID metodoPagamentoId) {
        MetodoPagamento metodo = metodoPagamentoRepository.findByIdAndUsuarioId(metodoPagamentoId, usuarioId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Método de pagamento não encontrado ou não pertence a este usuário."));

        metodoPagamentoRepository.delete(metodo);
    }
}
