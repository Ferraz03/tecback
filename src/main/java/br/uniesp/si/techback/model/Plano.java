package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity

public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "O código do plano obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "codigo", length = 20, nullable = false, unique = true)
    private PlanoCodigo codigo;

    @NotNull(message = "O limite diário é obrigatório")
    @Min(value = 0, message = "O limite diário não pode ser negativo")
    @Column(name = "limite_diario", nullable = false)
    private Integer limiteDiario;

    @NotNull(message = "O número de streams simultâneos é obrigatório")
    @Min(value = 0, message = "O número de streams simultâneos não pode ser negativo")
    @Column(name = "streams_simultaneos", nullable = false)
    private Integer streamsSimultaneos;
}
