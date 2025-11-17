// Crie este novo arquivo: br/uniesp/si/techback/exception/GlobalExceptionHandler.java

package br.uniesp.si.techback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Armadilha para a nossa exceção de "Não Encontrado"
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ProblemDetail> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Recurso Não Encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    // Armadilha para a nossa exceção de "Conflito de Dados"
    @ExceptionHandler(ConflitoDeDadosException.class)
    public ResponseEntity<ProblemDetail> handleConflitoDeDados(ConflitoDeDadosException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Conflito de Dados");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    // Armadilha para a exceção de duplicidade do ConteudoService (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException e) {
        // Podemos checar a mensagem para ter certeza que é o erro que queremos tratar como CONFLICT
        if (e.getMessage().contains("já existe")) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
            problemDetail.setTitle("Conflito de Dados");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
        }
        // Se for outro IllegalArgumentException, tratamos como Bad Request
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Requisição Inválida");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    // Armadilha para erros de validação do @Valid (Bean Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de Validação");
        problemDetail.setDetail("Um ou mais campos contêm dados inválidos.");
        problemDetail.setProperty("erros", errors); // Adiciona um campo customizado com os detalhes

        return ResponseEntity.badRequest().body(problemDetail);
    }
}