package com.lucasti.product.exceptions;

public class DepartamentNotFoundException extends RuntimeException {

    public DepartamentNotFoundException(String message) {
        super(message);
    }

    public DepartamentNotFoundException() {
        super("Departament not found");
    }
}
