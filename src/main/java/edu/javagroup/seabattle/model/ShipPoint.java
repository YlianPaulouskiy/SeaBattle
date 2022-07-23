package edu.javagroup.seabattle.model;

import edu.javagroup.seabattle.model.parent.ModelValue;
import lombok.Getter;

/**
 * Класс хранит номер ячейки и её состояние
 *
 * @author Павловский Юлиан
 */
@Getter
public class ShipPoint extends ModelValue implements Comparable<ShipPoint> {

    /**
     * Поле которое хранит номер ячейки
     */
    private final int point;

    public ShipPoint(int point, int value) {
        super(value);
        this.point = point;
    }

    @Override
    public int compareTo(ShipPoint shipPoint) {
        return Integer.compare(point, shipPoint.point);
    }
}
