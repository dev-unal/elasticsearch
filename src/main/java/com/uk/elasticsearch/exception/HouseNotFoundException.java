package com.uk.elasticsearch.exception;

public class HouseNotFoundException extends Exception{
	private static final long serialVersionUID = -8302887184488247964L;

	public HouseNotFoundException(String msg) {
		super(msg);
	}
}
