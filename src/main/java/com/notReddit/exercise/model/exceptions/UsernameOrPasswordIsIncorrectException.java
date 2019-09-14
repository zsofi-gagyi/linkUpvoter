package com.notReddit.exercise.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="username and/or password is incorrect")
public class UsernameOrPasswordIsIncorrectException extends RuntimeException {
}
