package edu.javagroup.seabattle.exception;

/**
 * Класс хранит исключение, связанное с неправильной стороной
 * MINE или ENEMY
 *
 * @author Павловский Юлиан
 */
public class SideNotFoundException extends RuntimeException {

    /**
     * Конструктор без параметров, выводит определенное
     * сообщение при возникновении исключения
     */
    public SideNotFoundException() {
        this("Уточните сторону (MINE or ENEMY)");
    }

    /**
     * Конструктор со строковым параметром,
     * который будет выводится в случае возникновения исключения
     *
     * @param message сообщение исключения
     */
    public SideNotFoundException(String message) {
        super(message);
    }
}
