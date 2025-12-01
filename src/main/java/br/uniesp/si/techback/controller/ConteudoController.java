package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.model.Tipo;
import br.uniesp.si.techback.service.ConteudoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/conteudos")
@RequiredArgsConstructor
public class ConteudoController {

    private final ConteudoService conteudoService;

    @GetMapping
    public ResponseEntity<Page<Conteudo>> listarConteudos(
            @RequestParam(required = false) Tipo tipo,
            @RequestParam(required = false) Genero genero,
            Pageable pageable) {
        Page<Conteudo> conteudos = conteudoService.listarFiltradoEPaginado(tipo, genero, pageable);
        return ResponseEntity.ok(conteudos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conteudo> detalharConteudo(@PathVariable UUID id) {
        Conteudo conteudo = conteudoService.buscarPorId(id);
        return ResponseEntity.ok(conteudo);
    }

    @PostMapping
    public ResponseEntity<Conteudo> criarConteudo(@Valid @RequestBody Conteudo conteudo) {
        Conteudo conteudoSalvo = conteudoService.criar(conteudo);
        return ResponseEntity.status(HttpStatus.CREATED).body(conteudoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conteudo> atualizarConteudo(
            @PathVariable UUID id,
            @Valid @RequestBody Conteudo conteudo) {

        Conteudo conteudoAtualizado = conteudoService.atualizar(id, conteudo);
        return ResponseEntity.ok(conteudoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConteudo(@PathVariable UUID id) {
        conteudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/top")
    public ResponseEntity<List<Conteudo>> topConteudos(@RequestParam(defaultValue = "5") int n) {
        List<Conteudo> conteudos = conteudoService.buscarTopNConteudos(n);
        return ResponseEntity.ok(conteudos);
    }

    @GetMapping("/apos-ano/{ano}")
    public ResponseEntity<List<Conteudo>> conteudosAposAno(@PathVariable Integer ano) {
        List<Conteudo> conteudos = conteudoService.buscarConteudosLancadosDepoisDe(ano);
        return ResponseEntity.ok(conteudos);
    }

    @GetMapping("/com-trailer")
    public ResponseEntity<List<Conteudo>> conteudosComTrailer() {
        List<Conteudo> conteudos = conteudoService.buscarConteudosComTrailer();
        return ResponseEntity.ok(conteudos);
    }
}