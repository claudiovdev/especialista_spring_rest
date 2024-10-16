package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir" +
            " entre em contato com o adminstrador do sistema";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers,status,request);
        }else if (rootCause instanceof PropertyBindingException){
            return handleInvalidPropertyBindingException((PropertyBindingException) rootCause, rootCause, headers, status,request);
        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSILVE;
        String detail = "O corpo da requisição é invalido. Verifique erro de sintaxe.";
        Problem problem = createProbemBuilder(status, problemType,detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),HttpStatus.NOT_FOUND,request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException){
            return handMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex,headers, status, request );
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s que você tentou acessar, é inexistente", ex.getRequestURL());

        Problem problem = createProbemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers,status,request);
                //super.handleNoHandlerFoundException(ex, headers, status, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request, BindingResult bindingResult) {
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;


        List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError){
                        name = ((FieldError) objectError).getField();
                    }
                    return Problem.Field.builder()
                            .nome(name)
                            .userMessage(message).build();
                }).collect(Collectors.toList());

        Problem problem = createProbemBuilder(status, problemType, detail)
                .userMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
                .fields(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleExemption(Exception ex, WebRequest request){

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICO_USUARIO_FINAL;

        Problem problem = createProbemBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),status,request);
    }


    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacaoException(ValidacaoException ex, WebRequest request){
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<Problem.Field> problemFields = ex.getBindingResult().getFieldErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                    String name = objectError.getObjectName();

                    if (objectError instanceof FieldError){
                        name = ((FieldError) objectError).getField();
                    }

                    return Problem.Field.builder()
                            .nome(name)
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());

        Problem problem = createProbemBuilder(status, problemType, detail)
                .userMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
                .fields(problemFields)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),status,request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){

            HttpStatus status = HttpStatus.NOT_FOUND;
            ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
            String detail = ex.getMessage();
            Problem problem = createProbemBuilder(status, problemType,detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),HttpStatus.NOT_FOUND,request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();
        Problem problem = createProbemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request){

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProbemBuilder(status,problemType, detail)
                .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(),HttpStatus.CONFLICT,request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null){
            body = Problem.builder()
                    .timestemp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestemp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
        String detail = String.format("O parametro de URL %s recebeu o valor %s , que é do tipo invalid. Corrija " +
                "e informe um valor com o tipo %s",ex.getName(),ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProbemBuilder(status,problemType, detail ).build();

        return handleExceptionInternal(ex,problem,headers, status, request);
    }



    private ResponseEntity<Object> handleInvalidPropertyBindingException(PropertyBindingException ex, Throwable rootCause, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String patch = ex.getPath().stream().map(reference -> reference.getFieldName()).collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSILVE;
        String detail = String.format("O atributo %s é um parametro que não existe no objeto", patch);
        Problem problem = createProbemBuilder(status,problemType, detail)
                .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex,problem,headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream().map(reference -> reference.getFieldName()).collect(Collectors.joining("."));

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSILVE;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s' que é um tipo invalido. corrija e informe um valor compativel com o tipo %s", path,
                ex.getValue(), ex.getTargetType().getSimpleName());


        Problem problem = createProbemBuilder(status,problemType, detail)
                .userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex,problem,headers, status, request);
    }


    private Problem.ProblemBuilder createProbemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail)
                .timestemp(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss"))));
    }
}
