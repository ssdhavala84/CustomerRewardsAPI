package com.customerrewards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomerRewardExceptionController {

	@ResponseBody
	@ExceptionHandler(CustomerRewardException.class)
	public ResponseEntity<StatusMessage> exception(CustomerRewardException c) {

		StatusMessage sm = new StatusMessage(c.getCode(), c.getMessage());

		if (c.getCode() == 400) {
			return new ResponseEntity<>(sm, HttpStatus.BAD_REQUEST);
		} else if (c.getCode() == 404) {
			return new ResponseEntity<>(sm, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(sm, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
