package edu.javagroup.seabattle.singleton;

/**
 * Синглетон, который хранит значение перехода хода.
 *
 * @author Павловский Юлиан
 */
public class MyStepSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static MyStepSingleton instance;

    /**
     * Переменная, хранящая значение: твой ход или нет
     */
    private final boolean step;

    /**
     * Конструктор, который устанавливает текущее значение хода
     *
     * @param step значение ходпа: твой или нет
     */
    private MyStepSingleton(boolean step) {
        this.step = step;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param step значение хода, которое устанавливается в синглетон, если step != null
     * @return текущее положение синглетона
     */
    public static MyStepSingleton instance(Boolean step) {
        if (instance == null) {
            instance = new MyStepSingleton(true);
        }
        if (step != null) {
            instance = new MyStepSingleton(step);
        }
        return instance;
    }

    /**
     * Метод возвращает переход хода
     *
     * @return true, если ход переходит к соперникку
     */
    public Boolean myStep() {
        return step;
    }

}
