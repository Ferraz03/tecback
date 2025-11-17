package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.URL;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "conteudo")
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 200, message = "O título não pode exceder 200 caracteres.")
    @Column(name = "titulo", length = 200, nullable = false)
    private String titulo;

    @NotNull(message = "O tipo de conteúdo é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 10, nullable = false)
    private Tipo tipo;

    @NotNull(message = "O ano é obrigatório.")
    @Min(value = 1888, message = "O ano não pode ser anterior a 1888.")
    @Max(value = 2100, message = "O ano não pode ser posterior a 2100.")
    @Column(name = "ano", nullable = false)
    private Integer ano;

    @NotNull(message = "A duração é obrigatória.")
    @Min(value = 1, message = "A duração deve ser de no mínimo 1 minuto.")
    @Max(value = 999, message = "A duração não pode exceder 999 minutos.")
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @NotNull(message = "A relevância é obrigatória.")
    @DecimalMin(value = "0.0", message = "A relevância não pode ser negativa.")
    @Digits(integer = 2, fraction = 2, message = "A relevância deve ter no máximo 2 dígitos inteiros e 2 decimais.")
    @Column(name = "relevancia", nullable = false, precision = 4, scale = 2)
    private BigDecimal relevancia;

    @Size(max = 2000, message = "A sinopse não pode exceder 2000 caracteres.")
    @Column(name = "sinopse", columnDefinition = "TEXT")
    private String sinopse;

    @URL(message = "O formato da URL do trailer é inválido.")
    @Size(max = 500, message = "A URL do trailer não pode exceder 500 caracteres.")
    @Column(name = "trailer_url", length = 500)
    private String trailerUrl;

    @NotBlank(message = "O gênero não pode ser vazio se for preenchido.")
    @Size(max = 50, message = "O gênero não pode exceder 50 caracteres.")
    @Column(name = "genero", length = 50)
    private Genero genero;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;
}
