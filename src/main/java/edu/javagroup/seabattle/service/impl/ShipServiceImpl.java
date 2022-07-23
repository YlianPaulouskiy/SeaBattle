package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import edu.javagroup.seabattle.singleton.ForbiddenCellsSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import edu.javagroup.seabattle.singleton.ShipStorageSingleton;
import edu.javagroup.seabattle.util.NumberUtils;
import edu.javagroup.seabattle.service.ShipService;
import org.springframework.stereotype.Component;

import java.util.*;

import static edu.javagroup.seabattle.constants.Constants.DECK;
import static edu.javagroup.seabattle.constants.Constants.VERTICAL_COORDINATE;

/**
 * Класс реализует интерфейс ShipService
 *
 * @author Павловский Юлиан
 */
@Component
public class ShipServiceImpl implements ShipService {

    /**
     * Переменная для хранения всех ячеек игрового поля, где установлены корабли
     */
    private List<ShipPoint> coordinateList;

    @Override
    public boolean checkShipCount() {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());
        Map<String, Integer> shipMap = new HashMap<>(4);
        for (int i = 4; i >= 1; i--) {
            shipMap.put(i + DECK, findShipDeck(i));
        }
        ShipStorageSingleton.instance(shipMap);
        for (int i = 4; i >= 1; i--) {
            if (shipMap.get(i + DECK) != 5 - i) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int checkShipCount(int deckCount) {
        getCoordinateList(MinePanelSingleton.instance(null).getPanel());

        if (!ImReadySingleton.instance(null).imReady()) {
            return findShipDeck(deckCount);
        } else {
            return findShipDeck(deckCount, 2); // 2 - корабль подбит
        }
    }

    /**
     * Метод устанавливает всю инфу собранную по вертикали и горинтали методами
     * getVerticalCoordinateList и getHorizontalCoordinateList соответственно
     * в переменную coordinateList
     *
     * @param horizontalLineList лист со вссеми горизонтальными линиями на поле
     */
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

    /**
     * Метод перебирает всю игровую панель по горизонтали и записывает
     * каждую ячейку в лист ShipPoint
     *
     * @param horizontalLineList лист со вссеми горизонтальными линиями на поле
     * @return лист ShipPoint, который содержит инфу о каждой ячейке,
     * выбранной по горизонтали
     */
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

    /**
     * Метод перебирает всю игровую панель по вертикали и записывает
     * каждую ячейку в лист ShipPoint
     *
     * @param horizontalLineList лист со вссеми горизонтальными линиями на поле
     * @return лист ShipPoint, который содержит инфу о каждой ячейке,
     * выбранной по вертикали
     */
    private List<ShipPoint> getVerticalCoordinateList(List<HorizontalLine> horizontalLineList) {
        List<ShipPoint> shipPoints = new ArrayList<>(110);
        int coordinateCount = 111;
        for (int horizontalCount = 0; horizontalCount < 10; horizontalCount++) {
            for (int verticalCount = 0; verticalCount < 10; verticalCount++) {
                if (horizontalLineList.get(verticalCount).getRow() == VERTICAL_COORDINATE.charAt(verticalCount)) {
                    shipPoints.add(new ShipPoint(coordinateCount, horizontalLineList.get(verticalCount)
                            .getPointElementList().get(horizontalCount).getValue()));
                }
                coordinateCount++;
            }
            shipPoints.add(new ShipPoint(coordinateCount, 0));
            coordinateCount++;
        }
        return shipPoints;
    }

    /**
     * Метод находит количество установленных кораблей указанной размерности
     *
     * @param sizeDeck размер корабля
     * @return количество установленных кораблей указанной размерности
     */
    private int findShipDeck(int sizeDeck) {
        int shipCount = 0;
        StringBuilder values = new StringBuilder();
        coordinateList.forEach(shipPoint -> values.append(shipPoint.getValue()));
        for (String ship : values.toString().split("0+")) {
            shipCount = (sizeDeck == ship.length()) ? ++shipCount : shipCount;
        }
        return (sizeDeck == 1 ? shipCount / 5 : shipCount);
    }

    /**Метод находит количество кораблей указанной размерности
     * @param sizeDeck размер корабля
     * @param splitValue значение по которому будем искать корабли
     * @return количество кораблей указанной размерности
     */
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

    /**
     * Метод осуществляет автоматическую расстановку кораблей по всем правилам
     */
    public void autoSetShipPoints() {
        //хранить основной лист
        List<HorizontalLine> horizontalLineList = new ArrayList<>();
        for (int i = 0; i < VERTICAL_COORDINATE.length(); i++) {
            horizontalLineList.add(new HorizontalLine(VERTICAL_COORDINATE.charAt(i)));
        }
        //при нажатии кнопки чистим запрещенные клетки
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().clear();
        //добавлем корабли
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
        //устанавливаем нашу мапу в синглетон
        MinePanelSingleton.instance(horizontalLineList);
    }

    /**
     * Метод ищет координату для установки коробля и устанавливает его,
     * если координата проходит все проверки
     *
     * @param horizontalLineList лист со вссеми горизонтальными линиями на поле
     * @param sizeShip           размер корабля, который нужно установить
     */
    private void setDeckShip(List<HorizontalLine> horizontalLineList, int sizeShip) {
        Random random = new Random();
        // координата куда мы будем ставить корабль
        int verticalCoordinate = random.nextInt(11 - sizeShip);
        int horizontalCoordinate = random.nextInt(11 - sizeShip);
        // выбираем как будем ставить по горизонтали или вертикали
        boolean isHorizontal = random.nextBoolean();

        // ищем клетку, где можно поставить корабль, чтобы они не налезали друг на друга
        while (isForbiddenCell(horizontalLineList, verticalCoordinate,
                horizontalCoordinate, isHorizontal, sizeShip)) {
            verticalCoordinate = random.nextInt(11 - sizeShip);
            horizontalCoordinate = random.nextInt(11 - sizeShip);
        }
        //если по горизонтали, занимаем нужные нам клетки по горизонтали
        // иначе по вертикали
        if (isHorizontal) {
            for (int i = 0; i < sizeShip; i++) {
                horizontalLineList.get(verticalCoordinate)
                        .getPointElementList().get(horizontalCoordinate + i).setValue(1);
            }
        } else {
            for (int i = 0; i < sizeShip; i++) {
                horizontalLineList.get(verticalCoordinate + i)
                        .getPointElementList().get(horizontalCoordinate).setValue(1);
            }
        }
        //устанавливает все запрещенные клетки
        pullForbiddenMap(horizontalLineList, verticalCoordinate,
                horizontalCoordinate, isHorizontal, sizeShip);
    }

    // проверка на допустимость выьранного поля, чтобы на своем пути он не налез на другой корабль
    private boolean isForbiddenCell(List<HorizontalLine> horizontalLineList, int vertical,
                                    int horizontal, boolean isHorizontal, int deckShip) {
        if (isHorizontal) {
            for (int i = -1; i < deckShip + 1; i++) {
                if (horizontal + i >= 0 && horizontal + i < 10) {
                    if (ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap()
                            .getOrDefault(horizontalLineList.get(vertical).getRow()
                                            + NumberUtils.currentNumber(horizontalLineList.get(vertical)
                                            .getPointElementList().get(horizontal + i).getCol()),
                                    false)) {
                        return true;
                    }
                }
            }
        } else {
            for (int i = -1; i < deckShip + 1; i++) {
                if (vertical + i >= 0 && vertical + i < 10) {
                    if (ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap()
                            .getOrDefault(horizontalLineList.get(vertical + i).getRow()
                                            + NumberUtils.currentNumber(horizontalLineList.get(vertical + i)
                                            .getPointElementList().get(horizontal).getCol()),
                                    false)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Метод устанавливает запрещенные ячейки, в которые корабль ставить нельзя
     * по бокам и по длине корабля
     *
     * @param horizontalLineList
     * @param vertical           координа корабля по вертикали, куда он становиться
     * @param horizontal         координата по горизонтали
     * @param isHorizontal       расположение корабля, т.е. вертикаль или горизонталь
     * @param deckCount          размер корабля
     */
    private void pullForbiddenMap(List<HorizontalLine> horizontalLineList, int vertical,
                                  int horizontal, boolean isHorizontal, int deckCount) {

        PointServiceImpl pointService = new PointServiceImpl(new PanelServiceImpl());

        // закрываем вырбранные клетки и по бокам
        for (HorizontalLine horizontalLine : horizontalLineList) {
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                if (pointElement.getValue() == 1) {
                    pointService.selectedCell(horizontalLine.getRow(), pointElement.getCol());
                    pointService.invalidCells(horizontalLine.getRow(), pointElement.getCol());
                }
            }
        }

        // закрываем поля в длину по вертикали и горизонтали, чтобы корабли не слипались
        if (isHorizontal) {
            if (horizontal - 1 >= 0 && (horizontal + deckCount) < 10) {
                pointService.selectedCell(horizontalLineList.get(vertical).getRow(),
                        horizontalLineList.get(vertical)
                                .getPointElementList().get(horizontal - 1).getCol());
                pointService.selectedCell(horizontalLineList.get(vertical).getRow(),
                        horizontalLineList.get(vertical)
                                .getPointElementList().get(horizontal + deckCount).getCol());
            }
        } else {
            if (vertical - 1 >= 0 && (vertical + deckCount) < 10 && horizontal - 1 >= 0) {
                pointService.selectedCell(horizontalLineList.get(vertical - 1).getRow(),
                        horizontalLineList.get(vertical - 1)
                                .getPointElementList().get(horizontal - 1).getCol());
                pointService.selectedCell(horizontalLineList.get(vertical + deckCount).getRow(),
                        horizontalLineList.get(vertical + deckCount)
                                .getPointElementList().get(horizontal - 1).getCol());
            }
        }

    }

}
