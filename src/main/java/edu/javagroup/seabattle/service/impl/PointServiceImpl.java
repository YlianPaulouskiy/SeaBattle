package edu.javagroup.seabattle.service.impl;


import edu.javagroup.seabattle.exception.SideNotFoundException;
import edu.javagroup.seabattle.model.HorizontalLine;
import edu.javagroup.seabattle.model.PointElement;
import edu.javagroup.seabattle.singleton.*;
import edu.javagroup.seabattle.util.NumberUtils;
import edu.javagroup.seabattle.util.StringUtils;
import edu.javagroup.seabattle.service.PointService;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static edu.javagroup.seabattle.constants.Constants.ENEMY;
import static edu.javagroup.seabattle.constants.Constants.MINE;

/**
 * Класс реализует интерфейс PointService
 *
 * @author Павловский Юлиан
 */
@Component
public class PointServiceImpl implements PointService {

    /**
     * Переменная для вызова методов класса PanelServiceImpl
     */
    private final PanelServiceImpl panelService;

    /**
     * Конструктор, который устанавливает переданную переменную класса
     * PanelServiceImpl в переменную текущего класса
     *
     * @param panelService экземпляр класса PanelServiceImpl
     */
    public PointServiceImpl(PanelServiceImpl panelService) {
        this.panelService = panelService;
    }

    @Override
    public void setShipPoint(char row, int col) {
        // если ячейка пуста, устанавливаем корабль
        if (isClearPoint(row, col)) {
            addShipPoint(row, col);
        } else {
            //иначе чистим ячейку
            clearShipPoint(row, col);
        }
    }

    @Override
    public boolean setSidePoint(String side, char row, int col, int value) {
        if (side.equals(MINE)) {
            //устанавливаем значение в поле MINE
            setMineSidePoint(row, col, value);
            //закраска поля вокруг подбитого корабля на моей панели
            setClearCellsWhenSHipDead(MINE);
            return true;
        } else if (side.equals(ENEMY)) {
            // устанавливаем значение в поле ENEMY
            setEnemySidePoint(row, col, value);
            //закраска поля вокруг подбитого корабля
            setClearCellsWhenSHipDead(ENEMY);
            return true;
        } else {
            throw new SideNotFoundException();
        }
    }


    @Override
    public boolean isClearPoint(char row, int col) {
        return isOccupiedCell(row, col, 0);
    }


    @Override
    public boolean getBomb(char row, int col) {
        // если в ячейке енет корабля
        if (isOccupiedCell(row, col, 0) || isOccupiedCell(row, col, 2)) {
            //устанавливаем значение промаха
            setSidePoint(MINE, row, col, 3);
            MyStepSingleton.instance(true);
        } else if (isOccupiedCell(row, col, 1)) {
            // иначе значение попадания
            setSidePoint(MINE, row, col, 2);
            MyStepSingleton.instance(false);
            return true;
        }
        return false;
    }

    /**
     * Метод выполняет несколько проверок и, если они все проходят,
     * то в ячейку row и col устанавливается значение палуба установлена(1)
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    private void addShipPoint(char row, int col) {
        //если ячейка не запрещена
        if (!ForbiddenCellsSingleton
                .instance(null)
                .getForbiddenCellsMap()
                .getOrDefault(row + NumberUtils.currentNumber(col), false)) {
            // если панель не пуста
            if (!panelService.isFullMinePanel()) {
                //устанавливаем палубу и запрещаем близ лежащие клетки для использования
                if (setSidePoint(MINE, row, col, 1)) {
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

    /**
     * Метод чистит указанную ячейку, устанавливает в нее значеие 0
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    private void clearShipPoint(char row, int col) {
        setSidePoint(MINE, row, col, 0);
        setForbiddenCells();
    }

    /**
     * Метод проверяет содержит ли ячейка данное значение
     *
     * @param row   Значение ячейки по вертикали(буква)
     * @param col   Значение ячейки по горизонтали(номер)
     * @param value Значение на которое проверяется ячейка
     * @return true, если значение ячейки соответствует проверяеммому
     */
    private boolean isOccupiedCell(char row, int col, int value) {
        return MinePanelSingleton.instance(null).getPanel().stream()
                .flatMap(horizontalLine -> horizontalLine.getPointElementList().stream()
                        .filter(pointElement -> horizontalLine.getRow() == row
                                && pointElement.getCol() == col))
                .anyMatch(pointElement -> pointElement.getValue() == value);
    }

    /**
     * Метод устанавливает запрещенные к использованию ячейки
     */
    private void setForbiddenCells() {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().clear();
        //находим ячейку со значением 1
        for (HorizontalLine horizontalLine : MinePanelSingleton.instance(null).getPanel()) {
            for (PointElement pointElement : horizontalLine.getPointElementList()) {
                if (pointElement.getValue() == 1) {
                    //запрещаем к использованию выбранные ячейки
                    selectedCell(horizontalLine.getRow(), pointElement.getCol());
                    //запрещаем рядом стоящие ячейки
                    invalidCells(horizontalLine.getRow(), pointElement.getCol());
                }
            }
        }
    }

    /**
     * Метод запрещает(добавляет значение в мапу ForbiddenSingleton) выбранную ячейку
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    public void selectedCell(char row, int col) {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(row + NumberUtils.currentNumber(col), true);
    }

    /**
     * Метод запрещает(добавляет значение в мапу ForbiddenSingleton)
     * ячейки рядом с указанными
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    public void invalidCells(char row, int col) {
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterBefore(row) + NumberUtils.numberBefore(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterBefore(row) + NumberUtils.numberAfter(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterAfter(row) + NumberUtils.numberBefore(col), true);
        ForbiddenCellsSingleton.instance(null).getForbiddenCellsMap().
                put(StringUtils.letterAfter(row) + NumberUtils.numberAfter(col), true);
    }

    /**
     * Метод устанавливает значение в MinePanelSingleton
     *
     * @param row   Значение ячейки по вертикали(буква)
     * @param col   Значение ячейки по горизонтали(номер)
     * @param value Устанавливаемое значение
     */
    private void setMineSidePoint(char row, int col, int value) {
        MinePanelSingleton.instance(null).getPanel().stream()
                .flatMap(horizontalLine -> horizontalLine.getPointElementList().stream()
                        .filter(pointElement -> horizontalLine.getRow() == row && pointElement.getCol() == col))
                .forEach(pointElement -> pointElement.setValue(value));
    }

    /**
     * Метод устанавливает значение в EnemyPanelSingleton
     *
     * @param row   Значение ячейки по вертикали(буква)
     * @param col   Значение ячейки по горизонтали(номер)
     * @param value Устанавливаемое значение
     */
    private void setEnemySidePoint(char row, int col, int value) {
        EnemyPanelSingleton.instance(null).getPanel().stream()
                .flatMap(horizontalLine -> horizontalLine.getPointElementList().stream()
                        .filter(pointElement -> horizontalLine.getRow() == row && pointElement.getCol() == col))
                .forEach(pointElement -> pointElement.setValue(value));
    }

    /**
     * Метод устанавливает значеие промаха по углам определенной ячейки
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    private void setAngleClearCell(String side, char row, int col) {
        if (side.equals(MINE)) {
            setMineSidePoint(StringUtils.letterBefore(row), col - 1, 3);
            setMineSidePoint(StringUtils.letterBefore(row), col + 1, 3);
            setMineSidePoint(StringUtils.letterAfter(row), col - 1, 3);
            setMineSidePoint(StringUtils.letterAfter(row), col + 1, 3);
        } else if (side.equals(ENEMY)) {
            setEnemySidePoint(StringUtils.letterBefore(row), col - 1, 3);
            setEnemySidePoint(StringUtils.letterBefore(row), col + 1, 3);
            setEnemySidePoint(StringUtils.letterAfter(row), col - 1, 3);
            setEnemySidePoint(StringUtils.letterAfter(row), col + 1, 3);
        } else {
            throw new SideNotFoundException();
        }
    }

    /**
     * Метод закрашивает ячейкив в которых точно корабля не будет
     */
    private void setClearCellsWhenSHipDead(String side) {
        if (side.equals(ENEMY)) {
            for (HorizontalLine horizontalLine : EnemyPanelSingleton.instance(null).getPanel()) {
                for (PointElement pointElement : horizontalLine.getPointElementList()) {
                    if (pointElement.getValue() == 2) {
                        setAngleClearCell(ENEMY, horizontalLine.getRow(), pointElement.getCol());
                    }
                }
            }
        } else if(side.equals(MINE)) {
            for (HorizontalLine horizontalLine : MinePanelSingleton.instance(null).getPanel()) {
                for (PointElement pointElement : horizontalLine.getPointElementList()) {
                    if (pointElement.getValue() == 2) {
                        setAngleClearCell(MINE, horizontalLine.getRow(), pointElement.getCol());
                    }
                }
            }
        } else {
            throw new SideNotFoundException();
        }
    }

}
