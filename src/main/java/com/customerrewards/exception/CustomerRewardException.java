package com.customerrewards.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRewardException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private int code;
	private String message;
}
