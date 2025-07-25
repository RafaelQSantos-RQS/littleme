package br.com.littleme.url_shortener.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SelfDeletionException extends RuntimeException {
    public SelfDeletionException(String message) {
        super(message);
    }
}
