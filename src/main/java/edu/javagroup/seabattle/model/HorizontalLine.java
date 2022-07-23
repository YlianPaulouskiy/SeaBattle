package edu.javagroup.seabattle.model;

import edu.javagroup.seabattle.model.parent.ModelRow;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит описание каждой ячейки горизонтальной линии
 *
 * @author Павловский Юлиан
 */
@Getter
@Setter
public class HorizontalLine extends ModelRow implements Comparable<HorizontalLine> {

    /**
     * Лист для ханения ячеек горизонтальной линии
     */
    List<PointElement> pointElementList = new ArrayList<>();

    /**
     * Конструктор, который заполняет каждую ячейку горизонтальной
     * линии значением по умолчанию ( == 0)
     *
     * @param row поле по вертикали
     */
    public HorizontalLine(char row) {
        super(row);
        for (int i = 0; i < 10; i++) {
            pointElementList.add(i, new PointElement(i + 1, 0));
        }

    }

    @Override
    public int compareTo(HorizontalLine horizontalLine) {
        return Integer.compare(getRow(), horizontalLine.getRow());
    }
}
