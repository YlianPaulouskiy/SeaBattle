package edu.javagroup.seabattle.exception;

public class AmountShipException extends RuntimeException {

    public AmountShipException() {
        this("Неверное количество караблей");
    }

    public AmountShipException(String message) {
        super(message);
    }
}
