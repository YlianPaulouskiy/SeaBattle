package edu.javagroup.seabattle.common.utils.impl;


import edu.javagroup.seabattle.singleton.ForbiddenCellsSingleton;
import edu.javagroup.seabattle.singleton.MinePanelSingleton;
import edu.javagroup.seabattle.util.NumberUtils;

public class PointService implements edu.javagroup.seabattle.service.PointService {

    private final PanelService panelService;

    public PointService(PanelService panelService) {
        this.panelService = panelService;
    }

    @Override
    public void setShipPoint(char row, int col) {

    }

    @Override
    public boolean setSidePoint(String side, char row, int col, int value) {
        return false;
    }

    @Override
    public boolean isClearPoint(char row, int col) {
        return false;
    }

    @Override
    public boolean getBomb(char row, int col) {
        return false;
    }

    public void setForbiddenCells() {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().clear();


    }
}
