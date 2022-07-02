package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.service.GameService;
import edu.javagroup.seabattle.service.PanelService;
import edu.javagroup.seabattle.service.PointService;
import edu.javagroup.seabattle.service.ShipService;
import edu.javagroup.seabattle.singleton.EnemyReadySingleton;
import edu.javagroup.seabattle.singleton.ForbiddenCellsSingleton;
import edu.javagroup.seabattle.singleton.ImReadySingleton;
import edu.javagroup.seabattle.singleton.SettingsSingleton;
import edu.javagroup.seabattle.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.HashMap;

@Component
public class GameServiceImpl implements GameService {

    private final PanelService panelService;
    private final PointService pointService;
    private final ShipService shipService;

    public GameServiceImpl(PanelService panelService, PointService pointService, ShipService shipService) {
        this.panelService = panelService;
        this.pointService = pointService;
        this.shipService = shipService;
    }

    @Override
    public boolean imReady() {
        if (isFullMinePanel()) {
            if (!checkShipCount()) {
                JOptionPane.showMessageDialog(null, "Корабли расставлены неправильно", "Внимание!", JOptionPane.WARNING_MESSAGE);
            } else {
                String ip = SettingsSingleton.instance(null).getSettingsByKey(Constants.ENEMY_IP_ADDRESS);
                if (StringUtils.isNotEmpty(ip)) {
                    ImReadySingleton.instance(true);
                    ForbiddenCellsSingleton.instance(new HashMap<>());
                } else {
                    JOptionPane.showMessageDialog(null, "Не указан ip-address врага", "Внимание!", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Еще не все корабли расставлены", "Внимание!", JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }

    @Override
    public boolean enemyReady() {
        EnemyReadySingleton.instance(true);
        boolean imReady = JOptionPane.showConfirmDialog(null, "Клятый враг спрашивает, готов ли ты быть поверженным?", "Окно подтверждения", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0;
        return imReady() && imReady;
    }

    @Override
    public void setShipPoint(char row, int col) {
        pointService.setShipPoint(row, col);
    }

    @Override
    public boolean isFullMinePanel() {
        return panelService.isFullMinePanel();
    }

    @Override
    public boolean getBomb(char row, int col) {
        return pointService.getBomb(row, col);
    }

    @Override
    public void setSidePoint(String side, char row, int col, int value) {
        pointService.setSidePoint(side, row, col, value);
    }

    @Override
    public boolean checkShipCount() {
        return shipService.checkShipCount();
    }

    @Override
    public int checkShipCount(int deckCount) {
        return shipService.checkShipCount(deckCount);
    }

    @Override
    public int howMuchIsLeft(String side) {
        return panelService.howMuchIsLeft(side);
    }

    @Override
    public boolean checkEndGame(String side) {
        return panelService.checkEndGame(side);
    }


}
