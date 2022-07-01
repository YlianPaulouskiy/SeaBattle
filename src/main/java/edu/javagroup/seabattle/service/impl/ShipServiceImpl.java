package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import edu.javagroup.seabattle.singleton.ShipStorageSingleton;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShipServiceImpl implements edu.javagroup.seabattle.service.ShipService {


    private List<ShipPoint> coordinateList;

    @Override
    public boolean checkShipCount() {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        Map<String, Integer> shipMap = new HashMap<>(4);
        for (int i = 4; i >= 1; i--) {
            shipMap.put(i + Constants.DECK, findShipDeck(i));
        }
        ShipStorageSingleton.instance(shipMap);
        for (int i = 4; i >= 1; i--) {
            if (shipMap.get(i + Constants.DECK) != 5 - i) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int checkShipCount(int deckCount) {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        return findShipDeck(deckCount);
    }

    public void getCoordinateList(List<HorizontalLine> horizontalLineList) {
        coordinateList = new ArrayList<>(220);

        coordinateList.addAll(getHorizontalCoordinateList(horizontalLineList));
        coordinateList.addAll(getVerticalCoordinateList(horizontalLineList));
        List<ShipPoint> shipPoints = new ArrayList<>(200);
        for (int i = 0, j = 1; j < coordinateList.size(); i++, j++) {
            if (coordinateList.get(i).getValue() == 0 && coordinateList.get(j).getValue() == 0) {
                shipPoints.add(coordinateList.get(i));
            }
        }
        coordinateList.removeAll(shipPoints);
        Collections.sort(coordinateList);
    }

    public List<ShipPoint> getHorizontalCoordinateList(List<HorizontalLine> horizontalLineList) {
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

    public List<ShipPoint> getVerticalCoordinateList(List<HorizontalLine> horizontalLineList) {
        List<ShipPoint> shipPoints = new ArrayList<>(110);
        int coordinateCount = 111;
        for (int horizontalCount = 0; horizontalCount < 10; horizontalCount++) {
            for (int verticalCount = 0; verticalCount < 10; verticalCount++) {
                if (horizontalLineList.get(verticalCount).getRow() == Constants.VERTICAL_COORDINATE.charAt(verticalCount)) {
                    shipPoints.add(new ShipPoint(coordinateCount, horizontalLineList.get(verticalCount).getPointElementList().get(horizontalCount).getValue()));
                }
                coordinateCount++;
            }
            shipPoints.add(new ShipPoint(coordinateCount, 0));
            coordinateCount++;
        }
        return shipPoints;
    }

    public int findShipDeck(int sizeDeck) {
        int shipCount = 0;
        StringBuilder values = new StringBuilder();
        coordinateList.forEach(shipPoint -> values.append(shipPoint.getValue()));
        for (String ship : values.toString().split("0+")) {
            shipCount = (sizeDeck == ship.length()) ? ++shipCount : shipCount;
        }
        return (sizeDeck == 1 ? shipCount / 5 : shipCount);
    }


}
