package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "metodo_pagamento")

public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "A bandeira é obrigatória.")
    @Column(name = "bandeira", length = 20, nullable = false)
    private String bandeira;

    @NotBlank(message = "Os últimos 4 dígitos são obrigatórios.")
    @Size(min = 4, max = 4, message = "O campo 'ultimos4' deve ter exatamente 4 dígitos.")
    @Column(name = "ultimos4", length = 4, nullable = false)
    private String ultimos4;

    @NotNull(message = "O mês de expiração é obrigatório.")
    @Min(value = 1, message = "Mês de expiração inválido.")
    @Max(value = 12, message = "Mês de expiração inválido.")
    @Column(name = "mes_exp", nullable = false)
    private Integer mesExp;

    @NotNull(message = "O ano de expiração é obrigatório.")
    @Min(value = 2024, message = "Ano de expiração inválido.")
    @Column(name = "ano_exp", nullable = false)
    private Integer anoExp;

    @NotBlank(message = "O nome do portador é obrigatório.")
    @Column(name = "nome_portador", length = 150, nullable = false)
    private String nomePortador;

    @NotBlank(message = "O token do gateway é obrigatório.")
    @Column(name = "token_gateway", length = 120, nullable = false, unique = true)
    private String tokenGateway;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

}
