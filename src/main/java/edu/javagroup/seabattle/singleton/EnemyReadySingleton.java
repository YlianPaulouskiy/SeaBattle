package edu.javagroup.seabattle.singleton;


/**
 * Синглетон, который хранит значение готовности соперника
 *
 * @author Павловский Юлиан
 */
public class EnemyReadySingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static EnemyReadySingleton instance;

    /**
     * Переменная, хранящая текущую готовность соперника к игре
     */
    private final boolean ready;

    /**
     * Конструктор, который устанавливает текущее значение готовности соперника
     *
     * @param ready готовность соперника к игре
     */
    private EnemyReadySingleton(boolean ready) {
        this.ready = ready;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param ready готовность соперника к игре
     * @return текущее состояние синглетона
     */
    public static EnemyReadySingleton instance(Boolean ready) {
        if (instance == null) {
            instance = new EnemyReadySingleton(false);
        }
        if (ready != null) {
            instance = new EnemyReadySingleton(ready);
        }
        return instance;
    }

    /**
     * Метод возвращает готовность соперника к игре
     *
     * @return true, если соперник готов
     */
    public Boolean enemyReady() {
        return ready;
    }
}
