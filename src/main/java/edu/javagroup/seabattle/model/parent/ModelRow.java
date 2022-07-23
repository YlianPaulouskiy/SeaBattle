package edu.javagroup.seabattle.model.parent;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс содержит поле для хранения значений по вертикали
 *
 * @author Павловский Юлиан
 */
@AllArgsConstructor
@Getter
public abstract class ModelRow {

    /**
     * Поле для хранения значения по вертикали
     */
    private final char row;

}
