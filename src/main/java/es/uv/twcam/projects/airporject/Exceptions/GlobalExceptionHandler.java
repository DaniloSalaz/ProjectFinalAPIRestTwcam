package es.uv.twcam.projects.airporject.Exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	Logger logger;
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<?> servletNoResultException(NoResultException e) {
		return new ResponseEntity<>(getErrorDetails(e), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(SeatsNotAvailableException.class)
	public ResponseEntity<?> servletSeatsNotAvailableException(SeatsNotAvailableException e) {
		return new ResponseEntity<>(getErrorDetails(e), HttpStatus.CONFLICT);
	}
	
	private ErrorDetails getErrorDetails(Exception e) {
		ErrorDetails errorDetails = new ErrorDetails();
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		//errorDetails.setDevErrorMessage(sw.toString());
		
		logger = LoggerFactory.getLogger(e.getClass());
		logger.error(errorDetails.getDevErrorMessage());
		
		errorDetails.setErrorsMessages(Arrays.asList(e.getMessage()));
		return errorDetails;
	}

}
