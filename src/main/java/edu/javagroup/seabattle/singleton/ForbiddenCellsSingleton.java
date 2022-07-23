package edu.javagroup.seabattle.singleton;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Синглетон, запрещенные ячейки
 *
 * @author Павловский Юлиан
 */
public class ForbiddenCellsSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static ForbiddenCellsSingleton instance;

    /**
     * Переменная хранящая мапу запрещенных ячеек, где корабль ставить нельзя
     */
    @Getter
    private final Map<String, Boolean> forbiddenCellsMap;

    /**
     * Конструктор, который устанавливает мапу запрещенных ячеек
     *
     * @param forbiddenCellsMap лист
     */
    private ForbiddenCellsSingleton(Map<String, Boolean> forbiddenCellsMap) {
        this.forbiddenCellsMap = forbiddenCellsMap;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param forbiddenCellsMap мапа запрещенных ячеек
     * @return текущее состояние синглетона
     */
    public static ForbiddenCellsSingleton instance(Map<String, Boolean> forbiddenCellsMap) {
        if (instance == null) {
            instance = new ForbiddenCellsSingleton(new HashMap<>());
        }
        if (forbiddenCellsMap != null && forbiddenCellsMap.size() > 0) {
            instance = new ForbiddenCellsSingleton(forbiddenCellsMap);
        }
        return instance;
    }

}
