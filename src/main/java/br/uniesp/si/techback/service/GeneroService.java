package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public List<Genero> listar() {
        return generoRepository.findAll();
    }

    public Genero buscarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Genero n o encontrado com o ID: " + id));
    }

    @Transactional
    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }

    @Transactional
    public Genero atualizar(Long id, Genero genero) {
        // Verifica se o filme existe
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Genero n o encontrado com o ID: " + id);
        }

        // Atualiza o ID do filme para o valor recebido
        genero.setId(id);

        // Salva o filme atualizado
        return generoRepository.save(genero);
    }

    @Transactional
    public void excluir(Long id) {
        // Verifica se o filme existe
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Genero n o encontrado com o ID: " + id);
        }

        // Exclui o filme
        generoRepository.deleteById(id);
    }
}
