package edu.javagroup.seabattle.common.utils.impl;


import edu.javagroup.seabattle.constants.Constants;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.singleton.*;
import edu.javagroup.seabattle.util.NumberUtils;
import edu.javagroup.seabattle.util.StringUtils;

import javax.swing.*;

public class PointService implements edu.javagroup.seabattle.service.PointService {

    private final PanelService panelService;

    public PointService(PanelService panelService) {
        this.panelService = panelService;
    }

    @Override
    public void setShipPoint(char row, int col) {
        if (isClearPoint(row, col)) {
            addShipPoint(row, col);
        } else {
            clearShipPoint(row, col);
        }
    }

    @Override
    public boolean setSidePoint(String side, char row, int col, int value) {

        if (side.equals(Constants.MINE)) {
           setMineSidePoint(row, col, value);
            return true;
        } else if (side.equals(Constants.ENEMY)) {
            setEnemySidePoint(row, col, value);
            return true;
        }
        return false;
    }


    @Override
    public boolean isClearPoint(char row, int col) {
        return isOccupiedCell(row, col, 0);
    }

    @Override
    public boolean getBomb(char row, int col) {

        if (isOccupiedCell(row, col, 0) || isOccupiedCell(row, col, 2)) {
            setSidePoint(Constants.MINE, row, col, 3);
            MyStepSingleton.instance(true);
        } else if (isOccupiedCell(row, col, 1)) {
            setSidePoint(Constants.MINE, row, col, 2);
            MyStepSingleton.instance(false);
            return true;
        }
        return false;
    }


    public void addShipPoint(char row, int col) {
        if (!ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().get(row + NumberUtils.currentNumber(col))) {
            if (!panelService.isFullMinePanel()) {
                if (setSidePoint(Constants.MINE, row, col, 1)) {
                    setForbiddenCells();
                } else {
                    JOptionPane.showMessageDialog(null, "Нельзя использовать эту ячейку", "Внимание!", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Уже занято допустимое количество ячеек", "Внимание!", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Не удалось использовать эту ячейку", "Внимание!", JOptionPane.WARNING_MESSAGE);
        }
    }


    public void clearShipPoint(char row, int col) {
        setSidePoint(Constants.MINE, row, col, 0);
        setForbiddenCells();
    }


    public boolean isOccupiedCell(char row, int col, int value) {
        return ShipStorageSingleton.instance(null).getShipMap().
                get(row + NumberUtils.currentNumber(col)) == value;
    }


    private void setForbiddenCells() {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().clear();

        for (HorizontalLine horizontalLine : MinePanelSingleton.instance(null).getPanel()) {
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                if (pointElement.getValue() == 1) {
                    selectedCell(horizontalLine.getRow(), pointElement.getCol());
                    invalidCells(horizontalLine.getRow(), pointElement.getCol());
                }
            }
        }
    }


    private void selectedCell(char row, int col) {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(row + NumberUtils.currentNumber(col), true);
    }


    private void invalidCells(char row, int col) {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterBefore(row) + NumberUtils.numberBefore(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterBefore(row) + NumberUtils.numberAfter(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterAfter(row) + NumberUtils.numberBefore(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterAfter(row) + NumberUtils.numberAfter(col), true);
    }


    private void setMineSidePoint(char row, int col, int value) {
        MinePanelSingleton.instance(null).getPanel().stream().flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                filter(pointElement -> horizontalLine.getRow() == row && pointElement.getCol() == col)).
                forEach(pointElement -> pointElement.setValue(value));
    }


    private void setEnemySidePoint(char row, int col, int value) {
        EnemyPanelSingleton.instance(null).getPanel().stream().flatMap(horizontalLine -> horizontalLine.getPointElementList().stream().
                filter(pointElement -> horizontalLine.getRow() == row && pointElement.getCol() == col)).
                forEach(pointElement -> pointElement.setValue(value));
    }

}
