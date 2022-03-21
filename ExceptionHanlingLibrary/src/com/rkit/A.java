package com.rkit;

public class A {
	ExceptionHandler handler = new ExceptionHandler();
	void m1() {
		try {
			throw new MyException();
		}catch(MyException e) {
			handler.handleException("com.rkit.A", "m1", e);
		}
	}

}
