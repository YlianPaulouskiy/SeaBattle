package edu.javagroup.seabattle.singleton;

import edu.javagroup.seabattle.model.HorizontalLine;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Синглетон, который хранит текущее значение твоего поля
 *
 * @author Павловский Юлиан
 */
public class MinePanelSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static MinePanelSingleton instance;

    /**
     * Переменная хранящая лист значений каждой горизонтальной линии на панели
     */
    @Getter
    private final List<HorizontalLine> panel;

    /**
     * Конструктор, который устанавливает текущее значение поля
     *
     * @param panel Лист значений твоего поля
     */
    private MinePanelSingleton(List<HorizontalLine> panel) {
        this.panel = panel;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param panel панель твоего поля, которая устанавливается в синглетон, если panel != null
     * @return текущее положение синглетона
     */
    public static MinePanelSingleton instance(List<HorizontalLine> panel) {
        if (instance == null) {
            instance = new MinePanelSingleton(new ArrayList<>());
        }
        if (panel != null && panel.size() > 0) {
            Collections.sort(panel);
            instance = new MinePanelSingleton(panel);
        }
        return instance;
    }

}
