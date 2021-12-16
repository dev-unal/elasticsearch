package com.uk.elasticsearch.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHelper {

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<Object> handleInvalidInputException(IllegalArgumentException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<Object> handleException(RuntimeException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = {HouseNotFoundException.class})
	protected ResponseEntity<Object> handleEntityNotFound(HouseNotFoundException ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
