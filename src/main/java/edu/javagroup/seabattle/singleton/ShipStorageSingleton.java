package edu.javagroup.seabattle.singleton;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ShipStorageSingleton {

    private static ShipStorageSingleton instance;
    @Getter
    private final Map<String, Integer> shipMap;

    private ShipStorageSingleton(Map<String, Integer> shipMap) {
        this.shipMap = shipMap;
    }

    public static ShipStorageSingleton instance(Map<String, Integer> shipMap) {
        if (instance == null) {
            instance = new ShipStorageSingleton(new HashMap<>());
        }
        if (shipMap != null && shipMap.size() > 0) {
            instance = new ShipStorageSingleton(shipMap);
        }
        return instance;
    }

}
