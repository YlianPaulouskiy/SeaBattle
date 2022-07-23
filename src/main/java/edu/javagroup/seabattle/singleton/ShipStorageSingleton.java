package edu.javagroup.seabattle.singleton;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Синглетон, который хранит расстановку кораблей на поле
 *
 * @author Павловский Юлиан
 */
public class ShipStorageSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static ShipStorageSingleton instance;

    /**
     * Переменная, хранящая расстановку кораблей
     */
    @Getter
    private final Map<String, Integer> shipMap;

    /**
     * Конструктор, который устанавливает текущую расстановку кораблей
     *
     * @param shipMap мапа с расстановкай кораблей
     */
    private ShipStorageSingleton(Map<String, Integer> shipMap) {
        this.shipMap = shipMap;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param shipMap расстановка кораблей на поле, которое устанавливается в синглетон,
     *                если shipMap != null
     * @return текущее положение синглетона
     */
    public static ShipStorageSingleton instance(Map<String, Integer> shipMap) {
        if (instance == null) {
            instance = new ShipStorageSingleton(new HashMap<>());
        }
        if (shipMap != null && shipMap.size() > 0) {
            instance = new ShipStorageSingleton(shipMap);
        }
        return instance;
    }

}
