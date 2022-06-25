package edu.javagroup.seabattle.singleton;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SettingsSingleton {

    private static SettingsSingleton instance;
    @Getter
    private final Map<String, String> settingsMap;

    private SettingsSingleton(Map<String, String> settingsMap) {
        this.settingsMap = settingsMap;
    }

    public static SettingsSingleton instance(Map<String, String> settingsMap) {
        if (instance == null) {
            instance = new SettingsSingleton(new HashMap<>());
        }
        if (settingsMap != null && settingsMap.size() > 0) {
            instance = new SettingsSingleton(settingsMap);
        }
        return instance;
    }

    public String getSettingsByKey(String key) {
        return settingsMap.get(key);
    }
}
