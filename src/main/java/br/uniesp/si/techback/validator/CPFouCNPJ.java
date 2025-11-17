// Crie este novo arquivo: br/uniesp/si/techback/validator/CPFouCNPJ.java

package br.uniesp.si.techback.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CPFouCNPJValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)

public @interface CPFouCNPJ {

    String message() default "CPF/CNPJ inválido";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}