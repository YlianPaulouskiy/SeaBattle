package edu.javagroup.seabattle.singleton;

import edu.javagroup.seabattle.model.HorizontalLine;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinePanelSingleton {

    private static MinePanelSingleton instance;
    @Getter
    private final List<HorizontalLine> panel;

    private MinePanelSingleton(List<HorizontalLine> panel) {
        this.panel = panel;
    }

    public static MinePanelSingleton instance(List<HorizontalLine> panel) {
        if (instance == null) {
            instance = new MinePanelSingleton(new ArrayList<>());
        }
        if (panel != null && panel.size() > 0) {
            Collections.sort(panel);
            instance = new MinePanelSingleton(panel);
        }
        return instance;
    }

}
