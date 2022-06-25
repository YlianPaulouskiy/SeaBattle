package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.exception.SideNotFoundException;
import edu.javagroup.seabattle.singleton.EnemyPanelSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
public class PanelServiceImpl implements edu.javagroup.seabattle.service.PanelService {


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
        return  20 == countSideValue(side, 2);
    }


    private int countSideValue(String side, int value) {
        if (side.equals(Constants.MINE)) {
            return (int) MinePanelSingleton.instance(null).getPanel().stream().
                    flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                            filter(pointElement -> pointElement.getValue() == value)).count();
        } else if (side.equals(Constants.ENEMY)) {
            return (int) EnemyPanelSingleton.instance(null).getPanel().stream().
                    flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                            filter(pointElement -> pointElement.getValue() == value)).count();
        } else throw new SideNotFoundException();
    }
}
