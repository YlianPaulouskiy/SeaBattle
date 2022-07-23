package edu.javagroup.seabattle.singleton;

/**
 * Синглетон, который хранит значение твоей готовности
 *
 * @author Павловский Юлиан
 */
public class ImReadySingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static ImReadySingleton instance;

    /**
     * Переменная, хранящая текущую твоей готовности к игре
     */
    private final boolean ready;

    /**
     * Конструктор, который устанавливает текущее значение твоей готовности к игре
     *
     * @param ready готовность к игре
     */
    private ImReadySingleton(boolean ready) {
        this.ready = ready;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param ready готовность к игре
     * @return текущее состояние синглетона
     */
    public static ImReadySingleton instance(Boolean ready) {
        if (instance == null) {
            instance = new ImReadySingleton(false);
        }
        if (ready != null) {
            instance = new ImReadySingleton(ready);
        }
        return instance;
    }

    /**
     * Метод возвращает твою готовность к игре
     *
     * @return true, если ты готов
     */
    public Boolean imReady() {
        return ready;
    }
}
