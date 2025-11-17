package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.PlanoCodigo;
import br.uniesp.si.techback.service.PlanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    @GetMapping
    public ResponseEntity<List<Plano>> listaPlanos() {
        List<Plano> planos = planoService.listarTodos();
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Plano> detalharPlano(@PathVariable PlanoCodigo codigo) {
        Plano plano = planoService.buscarPorCodigo(codigo);
        return ResponseEntity.ok(plano);
    }
}