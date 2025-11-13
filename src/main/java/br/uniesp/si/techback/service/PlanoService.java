package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.PlanoCodigo;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class PlanoService {

    private final PlanoRepository planoRepository;

    public List<Plano> listarTodos(){
        return planoRepository.findAll();
    }

    public Plano buscarPorCodigo(PlanoCodigo codigo){
        return planoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException
                        ("Plano com código " + codigo + " não encontrado"));
    }
}
