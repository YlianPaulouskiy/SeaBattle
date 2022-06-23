package edu.javagroup.seabattle.singleton;


import java.util.Collections;
import java.util.Map;

public class ForbiddenCellsSingleton {

    private static ForbiddenCellsSingleton instance;
    private final Map<String, Boolean> forbiddenCellsMap;

    private ForbiddenCellsSingleton(Map<String, Boolean> forbiddenCellsMap) {
        this.forbiddenCellsMap = forbiddenCellsMap;
    }

    public static ForbiddenCellsSingleton instance(Map<String, Boolean> forbiddenCellsMap) {
        if (instance == null) {
            instance = new ForbiddenCellsSingleton(Collections.emptyMap());
        }
        if (forbiddenCellsMap != null && forbiddenCellsMap.size() > 0) {
            instance = new ForbiddenCellsSingleton(forbiddenCellsMap);
        }
        return instance;
    }

    public Map<String, Boolean> getForbiddenCellsMap() {
        return forbiddenCellsMap;
    }
}
