package br.uniesp.si.techback.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoritoId implements Serializable {

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "conteudo_id")
    private UUID conteudoId;
}
