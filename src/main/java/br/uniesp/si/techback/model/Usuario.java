package br.uniesp.si.techback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    // GenerationType.IDENTITY = Número sequencial (1,2,3,4,5...) , Gerado pelo Banco de dados;
    // GenerationType.UUID = Texto aleatório (affj4328bfap08b...) ,  Gerado pelo JPA/Hibernate.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", length = 150, nullable = false)
    private String nomeCompleto;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // Pela convenção, 254 é maior tamanho possível para um path de email;
    // (Documento de referência - RFC 5321).
    @Column(length = 254, nullable = false, unique = true)
    private String email;

    // A senha não é guardada aqui, apenas a sua "impressão digital" segura;
    // Toda a lógica de segurança acontece na camada de serviço;
    // Um hash gerado pelo algoritmo BCrypt tem exatamente 60 caracteres.
    @Column(name = "senha_hash", length = 60, nullable = false)
    private String senhaHash;

    // Prática apenas contar os valores, sem caracteres especiais.
    @Column(name = "cpf_cnpj", length = 14, unique = true)
    private String cpfCnpj;

    // Enum criada para deixar os valores possíveis salvos.
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Perfil perfil;

    // Anotação que preenche automaticamente o campo com a data e hora atuais.
    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    // Anotação que atualiza automaticamente o campo com a data e hora atuais.
    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

}
