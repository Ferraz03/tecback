package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Conteudo;
import br.uniesp.si.techback.model.Tipo;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class ConteudoSpecification {

    public static Specification<Conteudo> comFiltros(Tipo tipo, String genero) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (tipo!= null){
                predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
            }

            if (genero != null && !genero.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("genero")), "%" + genero.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
