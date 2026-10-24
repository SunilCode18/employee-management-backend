package com.springboot.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.springboot.app.dto.ErrorResponseDTO;
import com.springboot.app.dto.ValidationErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex){
		
		
		Map<String, String> errors = new HashMap<>();
		for(var error  : ex.getBindingResult().getFieldErrors()) {
			errors.put(
					error.getField(), 
					error.getDefaultMessage()
				);
		}
		
		ValidationErrorResponseDTO dto = new ValidationErrorResponseDTO(
				HttpStatus.BAD_REQUEST.value(), 
				"Validation Failed", 
				errors
			);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
		
	}
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleEmployeeNotFound(EmployeeNotFoundException ex){
		
		ErrorResponseDTO errordto = new ErrorResponseDTO(
				HttpStatus.NOT_FOUND.value(), 
				ex.getMessage()
			);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errordto);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(
	        IllegalArgumentException ex) {

	    ErrorResponseDTO error = new ErrorResponseDTO(
	            HttpStatus.BAD_REQUEST.value(),
	            ex.getMessage()
	    );

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(error);
	}

}
