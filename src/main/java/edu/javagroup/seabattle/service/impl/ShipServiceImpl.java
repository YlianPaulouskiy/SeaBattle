package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShipServiceImpl implements edu.javagroup.seabattle.service.ShipService {


    private List<ShipPoint> coordinateList;

    @Override
    public boolean checkShipCount() {
        return false;
    }

    @Override
    public int checkShipCount(int deckCount) {
        return 0;
    }



    public List<ShipPoint> getVerticalCoordinateList(List<HorizontalLine> horizontalLineList) {
        List<ShipPoint> shipPoints = new ArrayList<>(110);
        int coordinateCount = 1;
        for (HorizontalLine horizontalLine : horizontalLineList) {
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                shipPoints.add(new ShipPoint(coordinateCount, pointElement.getValue()));
                coordinateCount++;
            }
            shipPoints.add(new ShipPoint(coordinateCount, 0));
            coordinateCount++;
        }
        return shipPoints;
    }

    public List<ShipPoint> getHorizontalCoordinateList(List<HorizontalLine> horizontalLineList) {
        List<ShipPoint> shipPoints = new ArrayList<>(110);
        int coordinateCount = 1;
        for (HorizontalLine horizontalLine : horizontalLineList) {
            int countLetter = 0;
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                if (horizontalLine.getRow() == Constants.VERTICAL_COORDINATE.charAt(countLetter)) {
                    shipPoints.add(new ShipPoint(coordinateCount, pointElement.getValue()));
                }
                countLetter++;
                coordinateCount++;
            }
            countLetter++;
            coordinateCount++;
        }

        return shipPoints;
    }


    private int findShipDeck(int deckCount) {
        return 0;
    }


}
