package br.com.jonas.s3integrationst.budget.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BucketException extends RuntimeException{
    public BucketException(String message) {
        super(message);
    }
}
