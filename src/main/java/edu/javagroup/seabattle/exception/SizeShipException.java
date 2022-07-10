package edu.javagroup.seabattle.exception;

public class SizeShipException extends RuntimeException {

    public SizeShipException() {
        this("Неверный размер корабля");
    }

    public SizeShipException(String message) {
        super(message);
    }
}
