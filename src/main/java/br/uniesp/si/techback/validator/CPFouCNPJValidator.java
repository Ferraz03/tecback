package br.uniesp.si.techback.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CPFouCNPJValidator implements ConstraintValidator<CPFouCNPJ, String> {

    private static final Pattern ONLY_DIGITS = Pattern.compile("\\d+");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        String digitsOnly = value.replaceAll("\\D", "");

        if (digitsOnly.length() == 11) {
            return isCpfValido(digitsOnly);
        } else if (digitsOnly.length() == 14) {
            return isCnpjValido(digitsOnly);
        }

        return false;
    }

    private boolean isCpfValido(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int[] peso = {10, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso[i];
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;
            if (dv1 != (cpf.charAt(9) - '0')) return false;

            soma = 0;
            int[] peso2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso2[i];
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;
            return dv2 == (cpf.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCnpjValido(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] peso = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += (cnpj.charAt(i) - '0') * peso[i];
            }
            int resto = soma % 11;
            int dv1 = (resto < 2) ? 0 : 11 - resto;
            if (dv1 != (cnpj.charAt(12) - '0')) return false;

            soma = 0;
            int[] peso2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            for (int i = 0; i < 13; i++) {
                soma += (cnpj.charAt(i) - '0') * peso2[i];
            }
            resto = soma % 11;
            int dv2 = (resto < 2) ? 0 : 11 - resto;
            return dv2 == (cnpj.charAt(13) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}