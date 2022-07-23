package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.service.PanelService;
import edu.javagroup.seabattle.exception.SideNotFoundException;
import edu.javagroup.seabattle.singleton.EnemyPanelSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import org.springframework.stereotype.Component;

import static edu.javagroup.seabattle.constants.Constants.ENEMY;
import static edu.javagroup.seabattle.constants.Constants.MINE;

/**
 * Класс реализует интерфейс PanelService
 *
 * @author Павловский Юлиан
 */
@Component
public class PanelServiceImpl implements PanelService {

    @Override
    public boolean isPanelEmpty() {
        return countSideValue(MINE, 0) == 100;
    }

    @Override
    public boolean isFullMinePanel() {
        return countSideValue(MINE, 1) == 20;
    }

    @Override
    public int howMuchIsLeft(String side) {
        return ImReadySingleton.instance(null).imReady()
                ? (20 - countSideValue(side, 2))
                : 0;
    }

    @Override
    public boolean checkEndGame(String side) {
        return 20 == countSideValue(side, 2);
    }

    /**
     * Метод считает количество ячеек с определенным значением
     *
     * @param side  сторона в которой нам нужно посчитать количество ячеек
     * @param value значение, которое необходимо найти
     * @return количество ячеек на строне side со значением value
     */
    private int countSideValue(String side, int value) {
        if (side.equals(MINE)) {
            return (int) MinePanelSingleton.instance(null).getPanel().stream()
                    .flatMap(horizontalLine -> horizontalLine.getPointElementList().stream()
                            .filter(pointElement -> pointElement.getValue() == value)).count();

        } else if (side.equals(ENEMY)) {
            return (int) EnemyPanelSingleton.instance(null).getPanel().stream()
                    .flatMap(horizontalLine -> horizontalLine.getPointElementList().stream()
                            .filter(pointElement -> pointElement.getValue() == value)).count();

        } else throw new SideNotFoundException();
    }
}
