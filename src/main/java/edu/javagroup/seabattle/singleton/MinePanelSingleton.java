package edu.javagroup.seabattle.singleton;

import edu.javagroup.seabattle.model.HorizontalLine;

import java.util.Collections;
import java.util.List;

public class MinePanelSingleton {

    private static MinePanelSingleton instance;
    private final List<HorizontalLine> panel;

    private MinePanelSingleton(List<HorizontalLine> panel) {
        this.panel = panel;
    }

    public static MinePanelSingleton instance(List<HorizontalLine> panel) {
        if (instance == null) {
            instance = new MinePanelSingleton(Collections.emptyList());
        }
        if (panel != null && panel.size() > 0) {
            Collections.sort(panel);
            instance = new MinePanelSingleton(panel);
        }
        return instance;
    }

    public List<HorizontalLine> getPanel() {
        return panel;
    }
}
