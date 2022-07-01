package edu.javagroup.seabattle.service.impl;

import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.model.ShipPoint;
import edu.javagroup.seabattle.service.GameService;
import edu.javagroup.seabattle.service.PanelService;
import edu.javagroup.seabattle.service.PointService;
import edu.javagroup.seabattle.service.ShipService;
import org.springframework.stereotype.Component;

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
        return false;
    }

    @Override
    public boolean enemyReady() {
        return false;
    }

    @Override
    public void setShipPoint(char row, int col) {

    }

    @Override
    public boolean isFullMinePanel() {
        return false;
    }

    @Override
    public boolean getBomb(char row, int col) {
        return false;
    }

    @Override
    public void setSidePoint(String side, char row, int col, int value) {

    }

    @Override
    public boolean checkShipCount() {
        return false;
    }

    @Override
    public int checkShipCount(int deckCount) {
        return 0;
    }

    @Override
    public int howMuchIsLeft(String side) {
        return 0;
    }

    @Override
    public boolean checkEndGame(String side) {
        return false;
    }


}
