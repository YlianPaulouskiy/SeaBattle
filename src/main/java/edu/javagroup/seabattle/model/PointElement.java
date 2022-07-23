package edu.javagroup.seabattle.model;

import edu.javagroup.seabattle.model.parent.ModelValue;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс хранит номер каждой ячейки и ее состояние
 *
 * @author Павловский Юлиан
 */
@Getter
@Setter
public class PointElement extends ModelValue implements Comparable<PointElement> {

    /**
     * Поле для хранения номера ячейки
     */
    private final int col;

    /**
     * Конструктор со всеми параметрами
     *
     * @param col   номер ячейки
     * @param value состояние ячейки
     */
    public PointElement(int col, int value) {
        super(value);
        this.col = col;
    }

    @Override
    public int compareTo(PointElement pointElement) {
        return Integer.compare(col, pointElement.col);
    }

}
