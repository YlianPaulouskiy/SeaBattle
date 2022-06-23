package edu.javagroup.seabattle.service;

public interface PanelService {

    boolean isPanelEmpty();
    boolean isFullMinePanel();
    int howMuchIsLeft(String side);
    boolean checkEndGame(String side);

}
