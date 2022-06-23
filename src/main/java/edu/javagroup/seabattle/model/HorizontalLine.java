package edu.javagroup.seabattle.model;

import edu.javagroup.seabattle.model.parent.ModelRow;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HorizontalLine extends ModelRow implements Comparable<HorizontalLine>{

    List<PointElement> pointElementList = new ArrayList<>();

    public HorizontalLine(char row) {
        super(row);
        for (int i = 0; i < 10; i++) {
            pointElementList.add(i, new PointElement(i+1, 0));
        }

    }

    @Override
    public int compareTo(HorizontalLine horizontalLine) {
        return Integer.compare(getRow(), horizontalLine.getRow());
    }
}
