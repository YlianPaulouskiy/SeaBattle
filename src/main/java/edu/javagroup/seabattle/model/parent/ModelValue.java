package edu.javagroup.seabattle.model.parent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс содержит поле для хранения текущего состояния ячейки игрового поля
 *
 * @author Павловский Юлиан
 */
@AllArgsConstructor
@Getter
@Setter
public abstract class ModelValue {

    /**
     * Поле для хранения состояния ячейки игрового поля
     */
    private int value;

}
