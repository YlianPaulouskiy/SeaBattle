package edu.javagroup.seabattle.singleton;


import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ForbiddenCellsSingleton {

    private static ForbiddenCellsSingleton instance;
    @Getter
    private final Map<String, Boolean> forbiddenCellsMap;

    private ForbiddenCellsSingleton(Map<String, Boolean> forbiddenCellsMap) {
        this.forbiddenCellsMap = forbiddenCellsMap;
    }

    public static ForbiddenCellsSingleton instance(Map<String, Boolean> forbiddenCellsMap) {
        if (instance == null) {
            instance = new ForbiddenCellsSingleton(new HashMap<>());
        }
        if (forbiddenCellsMap != null && forbiddenCellsMap.size() > 0) {
            instance = new ForbiddenCellsSingleton(forbiddenCellsMap);
        }
        return instance;
    }

}
