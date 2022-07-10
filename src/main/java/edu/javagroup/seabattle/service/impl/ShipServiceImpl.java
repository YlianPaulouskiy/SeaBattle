package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.exception.AmountShipException;
import edu.javagroup.seabattle.exception.SideNotFoundException;
import edu.javagroup.seabattle.exception.SizeShipException;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import edu.javagroup.seabattle.service.PointService;
import edu.javagroup.seabattle.singleton.ForbiddenCellsSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import edu.javagroup.seabattle.singleton.ShipStorageSingleton;
import edu.javagroup.seabattle.util.NumberUtils;
import edu.javagroup.seabattle.util.StringUtils;
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

    // FIXME: 05.07.2022 
    @Override
    public int checkShipCount(int deckCount) {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        if (checkCountAndSizeShip(deckCount)) {
            if (!ImReadySingleton.instance(null).imReady()) {
                return findShipDeck(deckCount);
            } else {
                return findShipDeck(deckCount, 2); // 2 - корабль подбит
            }
        } else {
            throw new AmountShipException();
        }
    }

    // FIXME: 05.07.2022 
    private boolean checkCountAndSizeShip(int deckCount) {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        if (deckCount > 4 || deckCount < 1) {
            throw new SizeShipException();
        }
        int countShip = findShipDeck(deckCount);
        switch (deckCount) {
            case 1:
                if (4 != countShip) return false;
                break;
            case 2:
                if (3 != countShip) return false;
                break;
            case 3:
                if (2 != countShip) return false;
                break;
            case 4:
                if (1 != countShip) return false;
                break;
        }
        return true;
    }

    private void getCoordinateList(List<HorizontalLine> horizontalLineList) {
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

    private List<ShipPoint> getHorizontalCoordinateList(List<HorizontalLine> horizontalLineList) {
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

    private List<ShipPoint> getVerticalCoordinateList(List<HorizontalLine> horizontalLineList) {
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

    private int findShipDeck(int sizeDeck, int splitValue) {
        int shipCount = 0;
        StringBuilder values = new StringBuilder();
        coordinateList.forEach(shipPoint -> values.append(shipPoint.getValue()));
        /* оставляем нужные нам ячейки
            0 - пустая, 1 - палуба установлена, 2 - палуба подбита, 3 - промах
        */
        for (String ship : values.toString().split("[^" + splitValue + "+]")) {
            shipCount = (sizeDeck == ship.length()) ? ++shipCount : shipCount;
        }
        return (sizeDeck == 1 ? shipCount / 5 : shipCount);
    }


    private int findShipDeck(int sizeDeck) {
        int shipCount = 0;
        StringBuilder values = new StringBuilder();
        coordinateList.forEach(shipPoint -> values.append(shipPoint.getValue()));
        for (String ship : values.toString().split("0+")) {
            shipCount = (sizeDeck == ship.length()) ? ++shipCount : shipCount;
        }
        return (sizeDeck == 1 ? shipCount / 5 : shipCount);
    }

    public void autoSetShipPoints() {
        //хранить основной лист
        List<HorizontalLine> horizontalLineList = new ArrayList<>();
        for (int i = 0; i < Constants.VERTICAL_COORDINATE.length(); i++) {
            horizontalLineList.add(new HorizontalLine(Constants.VERTICAL_COORDINATE.charAt(i)));
        }
        //при нажатии кнопки чистим запрещенные клетки
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().clear();

        setDeckShip(horizontalLineList, 4);
        setDeckShip(horizontalLineList, 3);
        setDeckShip(horizontalLineList, 3);
        setDeckShip(horizontalLineList, 2);
        setDeckShip(horizontalLineList, 2);
        setDeckShip(horizontalLineList, 2);
        setDeckShip(horizontalLineList, 1);
        setDeckShip(horizontalLineList, 1);
        setDeckShip(horizontalLineList, 1);
        setDeckShip(horizontalLineList, 1);


        MinePanelSingleton.instance(horizontalLineList);
    }

    private void setDeckShip(List<HorizontalLine> horizontalLineList, int sizeShip) {
        Random random = new Random();
        int verticalCoordinate = random.nextInt(11 - sizeShip);
        int horizontalCoordinate = random.nextInt(11 - sizeShip);

        boolean isHorizontal = isHorizontal();

        // clearPoint - чтобы корабли не налипали друг на друга
        while (isForbiddenCell(horizontalLineList, verticalCoordinate, horizontalCoordinate, isHorizontal, sizeShip)
            //        !isClearPoint(horizontalLineList, verticalCoordinate, horizontalCoordinate, isHorizontal, sizeShip)
        ) {
            verticalCoordinate = random.nextInt(11 - sizeShip);
            horizontalCoordinate = random.nextInt(11 - sizeShip);
        }

        if (isHorizontal) {
            for (int i = 0; i < sizeShip; i++) {
                horizontalLineList.get(verticalCoordinate).getPointElementList().get(horizontalCoordinate + i).setValue(1);
            }
        } else {
            for (int i = 0; i < sizeShip; i++) {
                horizontalLineList.get(verticalCoordinate + i).getPointElementList().get(horizontalCoordinate).setValue(1);
            }
        }
        pullForbiddenMap(horizontalLineList, verticalCoordinate, horizontalCoordinate, isHorizontal, sizeShip);
    }


    private boolean isForbiddenCell(List<HorizontalLine> horizontalLineList, int vertical, int horizontal, boolean isHorizontal, int deckShip) {
        if (isHorizontal) {
            for (int i = 0; i < deckShip; i++) {
                if (ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().getOrDefault(horizontalLineList.get(vertical).getRow()
                        + NumberUtils.currentNumber(horizontalLineList.get(vertical).getPointElementList().get(horizontal + i).getCol()), false)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < deckShip; i++) {
                if (ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().getOrDefault(horizontalLineList.get(vertical + i).getRow()
                        + NumberUtils.currentNumber(horizontalLineList.get(vertical + i).getPointElementList().get(horizontal).getCol()), false)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void pullForbiddenMap(List<HorizontalLine> horizontalLineList, int vertical, int horizontal, boolean isHorizontal, int deckCount) {

        PointServiceImpl pointService = new PointServiceImpl(new PanelServiceImpl());

        for (HorizontalLine horizontalLine : horizontalLineList) {
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                if (pointElement.getValue() == 1) {
                    pointService.selectedCell(horizontalLine.getRow(), pointElement.getCol());
                    pointService.invalidCells(horizontalLine.getRow(), pointElement.getCol());
                }
            }
        }
        if (isHorizontal) {
            if (horizontal >= 1 && (horizontal + deckCount) < 10) {
                pointService.selectedCell(horizontalLineList.get(vertical).getRow(),
                        horizontalLineList.get(vertical).getPointElementList().get(horizontal - 1).getCol());
                pointService.selectedCell(horizontalLineList.get(vertical).getRow(),
                        horizontalLineList.get(vertical).getPointElementList().get(horizontal + deckCount).getCol());
            }
        } else {
            if (vertical >= 1 && (vertical + deckCount) < 10) {
                pointService.selectedCell(horizontalLineList.get(vertical - 1).getRow(),
                        horizontalLineList.get(vertical - 1).getPointElementList().get(horizontal).getCol());
                pointService.selectedCell(horizontalLineList.get(vertical + deckCount).getRow(),
                        horizontalLineList.get(vertical + deckCount).getPointElementList().get(horizontal).getCol());
            }
        }

    }

    private boolean isHorizontal() {
        return new Random().nextBoolean();
    }
}
