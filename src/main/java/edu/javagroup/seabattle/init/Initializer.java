package edu.javagroup.seabattle.init;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.singleton.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс содержит методы, котоые устанавливают начальное
 * состояние для  синглетонов
 *
 * @author Павловский Юлиан
 */
public class Initializer {

    /**
     * Вызывает метод initPanels(), тем самым
     * инициализируя начальное состояние синглетонов
     */

    public void init() {
        initPanels();
    }

    /**
     * Устанавливает начальные значения для синглетонов
     */
    public void initPanels() {

        Map<String, Integer> shipStorageMap = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            shipStorageMap.put((i + 1) + Constants.DECK, 0);
        }
        ShipStorageSingleton.instance(shipStorageMap);

        List<HorizontalLine> mineHorizontalLineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mineHorizontalLineList.add(new HorizontalLine(Constants.VERTICAL_COORDINATE.charAt(i)));
        }
        MinePanelSingleton.instance(mineHorizontalLineList);

        List<HorizontalLine> enemyHorizontalLineList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            enemyHorizontalLineList.add(new HorizontalLine(Constants.VERTICAL_COORDINATE.charAt(i)));
        }
        EnemyPanelSingleton.instance(enemyHorizontalLineList);
    }
}
