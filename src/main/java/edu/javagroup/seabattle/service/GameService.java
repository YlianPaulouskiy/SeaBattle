package edu.javagroup.seabattle.service;

/**
 * Интерфейс который содержит основные методы из
 * PanelService, PointService и ShipService
 */
public interface GameService {

    /**
     * Метод выполняет некоторые проверки перед началом игры и
     * в зависимости от результатов устанавливает твою готовность к игре
     *
     * @return true, если все проверки пройдены
     */
    boolean imReady();

    /**
     * Метод узнает готовность сопернимка к игре
     *
     * @return true, если соперник готов
     */
    boolean enemyReady();

    /**
     * Метод вызывает этот метод из PointService
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     */
    void setShipPoint(char row, int col);

    /**
     * Метод вызывает этот метод из PanelService
     *
     * @return true, если на поле есть 20 ячеек со значением 1(палуба установлена)
     */
    boolean isFullMinePanel();

    /**
     * Метод вызывает этот метод из PointService
     *
     * @param row Значение ячейки по вертикали(буква)
     * @param col Значение ячейки по горизонтали(номер)
     * @return true, если корабль подбит
     */
    boolean getBomb(char row, int col);

    /**
     * Метод вызывает метод с таким же названием из PointService
     *
     * @param side  Сторона, в которую устанавливают значения ячейки
     * @param row   Значение ячейки по вертикали(буква)
     * @param col   Значение ячейки по горизонтали(номер)
     * @param value Значение, которое нужно установить в ячейку
     */
    void setSidePoint(String side, char row, int col, int value);

    /**
     * Вызыввает такой же метод из ShipService
     *
     * @return true, если количество кораблей верное
     */
    boolean checkShipCount();

    /**
     * Вызыввает такой же метод из PanelService
     *
     * @param side сторона на которой мы ищем количество не подбитых палуб
     * @return количество оставшихся ячеек со значением 1(палуба установлена)
     */
    int howMuchIsLeft(String side);

    /**
     * Вызыввает такой же метод из PanelService
     *
     * @param side сторона которую проверяем на конец игры
     * @return true, если количество ячеек со значением 2(корабль подбит) == 20
     */
    boolean checkEndGame(String side);

    /**
     * Вызыввает такой же метод из ShipService
     */
    void autoSetShipPoints();
}
