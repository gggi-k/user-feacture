package kr.submit.userfeature.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotVerifiedException extends RuntimeException {

    public NotVerifiedException(String message) {
        super(message);
    }

    public NotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotVerifiedException(Throwable cause) {
        super(cause);
    }

    public NotVerifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}