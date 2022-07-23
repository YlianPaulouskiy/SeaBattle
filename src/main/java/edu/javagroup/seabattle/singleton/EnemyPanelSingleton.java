package edu.javagroup.seabattle.singleton;

import edu.javagroup.seabattle.model.HorizontalLine;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Синглетон, который хранит текущее значение поля соперника
 *
 * @author Павловский Юлиан
 */

public class EnemyPanelSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static EnemyPanelSingleton instance;

    /**
     * Переменная хранящая лист значений каждой горизонтальной линии на панели
     */
    @Getter
    private final List<HorizontalLine> panel;

    /**
     * Конструктор, который устанавливает текущее значение поля
     *
     * @param panel лист значений твоего поля
     */
    private EnemyPanelSingleton(List<HorizontalLine> panel) {
        this.panel = panel;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param panel панель поля соперника, которая устанавливается в синглетон, если panel != null
     * @return текущее состояние синглетона
     */
    public static EnemyPanelSingleton instance(List<HorizontalLine> panel) {
        if (instance == null) {
            instance = new EnemyPanelSingleton(new ArrayList<>(0));
        }
        if (panel != null && panel.size() > 0) {
            Collections.sort(panel);
            instance = new EnemyPanelSingleton(panel);
        }
        return instance;
    }

}
