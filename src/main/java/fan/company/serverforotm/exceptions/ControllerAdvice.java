package fan.company.serverforotm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleException(ForbiddenException forbiddenException) {
        return new ResponseEntity<String>(forbiddenException.getMessage() + " " + forbiddenException.getType(), HttpStatus.FORBIDDEN);
    }
}
