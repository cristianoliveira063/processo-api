package br.com.processo.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7976145822383276723L;
    private final String code;
    private final HttpStatus status;

}
