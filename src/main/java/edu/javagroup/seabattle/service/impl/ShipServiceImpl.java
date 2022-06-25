package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import edu.javagroup.seabattle.singleton.ShipStorageSingleton;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShipServiceImpl implements edu.javagroup.seabattle.service.ShipService {


    private List<ShipPoint> coordinateList;

    // FIXME: 26.06.2022
    @Override
    public boolean checkShipCount() {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        Map<String, Integer> shipMap = new HashMap<>(4);
        for (int i = 4; i >= 1; i--) {
            shipMap.put(i + Constants.DECK, i);
        }
        ShipStorageSingleton.instance(shipMap);
        return shipMap.get(4+Constants.DECK) == 1 && shipMap.get(3+Constants.DECK) == 2
                && shipMap.get(2+Constants.DECK) == 3 && shipMap.get(1+Constants.DECK) == 4;
    }

    // FIXME: 26.06.2022
    @Override
    public int checkShipCount(int deckCount) {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        return findShipDeck(deckCount);
    }

    // FIXME: 26.06.2022
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
        coordinateList.sort(Comparator.comparing(ShipPoint::getPoint));
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
        int coordinateCount = 1;
        for (int horizontalCount = 0; horizontalCount < 10; horizontalCount++) {
            for (int verticalCount = 0; verticalCount < 10; verticalCount++) {
                if (horizontalLineList.get(verticalCount).getRow() == Constants.VERTICAL_COORDINATE.charAt(verticalCount)) {
                    shipPoints.add(new ShipPoint(coordinateCount, horizontalLineList.get(verticalCount).getPointElementList().get(horizontalCount).getValue()));
                }
            }
            shipPoints.add(new ShipPoint(coordinateCount, 0));
            coordinateCount++;
        }
        return shipPoints;
    }

    // FIXME: 26.06.2022
    public int findShipDeck(int sizeDeck) {
        int shipCount = 0;
        StringBuilder values = new StringBuilder();
        MinePanelSingleton.instance(null).getPanel().forEach(horizontalLine -> horizontalLine.getPointElementList().forEach(pointElement -> values.append(pointElement.getValue())));
        String[] ships = values.toString().split("0");
        for (String ship : ships) {
           shipCount = (sizeDeck == ship.length()) ? ++shipCount : shipCount;
        }
        return (sizeDeck == 1 ? shipCount/5 : shipCount);
    }


}
