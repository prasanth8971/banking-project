package org.tsp.Banking_System.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tsp.Banking_System.helper.ResponceStructure;


@ControllerAdvice
public class ExceptionController
{
	@ExceptionHandler(value=MyException.class)
	public ResponseEntity<ResponceStructure<String>>idNotFound(MyException ie)
	{
		ResponceStructure< String> structure=new ResponceStructure<String>();
		structure.setCode(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setMsg("request failed");
		structure.setData(ie.toString());
		return new ResponseEntity<ResponceStructure<String>>(structure, HttpStatus.NOT_ACCEPTABLE);
	}
}
