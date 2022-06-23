package edu.javagroup.seabattle.common.utils.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.singleton.EnemyPanelSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import org.springframework.stereotype.Component;


@Component
public class PanelService implements edu.javagroup.seabattle.service.PanelService {


    @Override
    public boolean isPanelEmpty() {
        return countSideValue("M", 0) == 100;
    }


    @Override
    public boolean isFullMinePanel() {
        return countSideValue("M", 1) == 20;
    }

    @Override
    public int howMuchIsLeft(String side) {
        return ImReadySingleton.instance(null).imReady() ?
                (20 - countSideValue(side, 2)) : 0;
    }

    @Override
    public boolean checkEndGame(String side) {
        return 20 == countSideValue(side, 2);
    }


    private int countSideValue(String side, int value) {
        return side.equals(Constants.MINE) ?
                (int) MinePanelSingleton.instance(null).getPanel().stream().
                        flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                                filter(pointElement -> pointElement.getValue() == value)).count() :
                (int) EnemyPanelSingleton.instance(null).getPanel().stream().
                        flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                                filter(pointElement -> pointElement.getValue() == value)).count();
    }

}
