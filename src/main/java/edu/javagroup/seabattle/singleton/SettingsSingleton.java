package edu.javagroup.seabattle.singleton;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Синглетон, который хранит ip адресс соперника
 *
 * @author Павловский Юлиан
 */
public class SettingsSingleton {

    /**
     * Переменная, хранящая текущее состояние синглетона
     */
    private static SettingsSingleton instance;

    /**
     * Переменная, хранящая значение ip адресса соперника
     */
    @Getter
    private final Map<String, String> settingsMap;

    /**
     * Конструктор, который устанавливает текущее значение ip адресса соперника
     *
     * @param settingsMap ip адресса соперника
     */
    private SettingsSingleton(Map<String, String> settingsMap) {
        this.settingsMap = settingsMap;
    }

    /**
     * Метод для доступа к синглетону, который если синнглетон еще не создан
     * содает экхемпляр класса, иначе возвращает существующий
     *
     * @param settingsMap значение ip адресса соперника, которое устанавливается в синглетон,
     *                    если settingsMap != null
     * @return текущее положение синглетона
     */
    public static SettingsSingleton instance(Map<String, String> settingsMap) {
        if (instance == null) {
            instance = new SettingsSingleton(new HashMap<>());
        }
        if (settingsMap != null && settingsMap.size() > 0) {
            instance = new SettingsSingleton(settingsMap);
        }
        return instance;
    }

    /**
     * Метод возвращает значение из мапы по ключу
     *
     * @return значение из мапы
     */
    public String getSettingsByKey(String key) {
        return settingsMap.get(key);
    }
}
