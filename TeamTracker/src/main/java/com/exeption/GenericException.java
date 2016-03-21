package com.exeption;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author M1031956
 */
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class GenericException extends RuntimeException {
	
	private static final Logger logger = Logger.getLogger(GenericException.class);
	
	private static final long serialVersionUID = 1L;
	public GenericException(Exception ex) {
		super(ex);
		logger.error("", ex);
	}
}