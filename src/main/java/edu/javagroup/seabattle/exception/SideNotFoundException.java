package edu.javagroup.seabattle.exception;

public class SideNotFoundException extends RuntimeException{

    public SideNotFoundException() {
        this("Уточните сторону (MINE or ENEMY)");
    }

    public SideNotFoundException(String message) {
        super(message);
    }
}
