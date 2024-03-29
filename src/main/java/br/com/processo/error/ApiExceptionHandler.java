package br.com.processo.error;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String NO_MESSSAGE_AVAILABLE = "No message available";
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    private final MessageSource apiErrorMessageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(final MethodArgumentNotValidException exception
            , final Locale locale) {
        final Stream<ObjectError> errors = exception.getBindingResult().getAllErrors().stream();

        final List<ErrorResponse.ApiError> apiErrors = errors
                .map(ObjectError::getDefaultMessage)
                .map(code -> toApiError(code, locale))
                .collect(toList());

        final ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(final InvalidFormatException exception
            , final Locale locale) {
        final String errorCode = "generic-1";
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getValue()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(final DataIntegrityViolationException exception
            , final Locale locale) {
        final String errorCode = "generic-1";
        final HttpStatus status = HttpStatus.CONFLICT;
        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale, exception.getCause()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException exception, final Locale locale) {
        final String errorCode = exception.getCode();
        final HttpStatus status = exception.getStatus();

        final ErrorResponse errorResponse = ErrorResponse.of(status, toApiError(errorCode, locale));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    public ErrorResponse.ApiError toApiError(final String code, final Locale locale, final Object... args) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, args, locale);
        } catch (final NoSuchMessageException e) {
            LOG.error("Could not find any message for {} code under {} locale", code, locale);
            message = NO_MESSSAGE_AVAILABLE;
        }

        return new ErrorResponse.ApiError(code, message);
    }

}
