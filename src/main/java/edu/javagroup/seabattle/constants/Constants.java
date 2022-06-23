package edu.javagroup.seabattle.constants;

public interface Constants {

    int[] LOCALHOST = {127, 0, 0, 1};
    String ENEMY_PROTOCOL = "http";
    String ENEMY_IP_ADDRESS = "enemyIpAddress";
    String ENEMY_PORT = "8080";
    String VERTICAL_COORDINATE = "ABCDEFGHIJ";
    String DECK = "deck";
    String BUTTON_PREFIX = "jButton";
    String MINE = "M";
    String ENEMY = "E";
    String M_BUTTON_PREFIX = BUTTON_PREFIX.concat(MINE);
    String E_BUTTON_PREFIX = BUTTON_PREFIX.concat(ENEMY);
    String BUTTON_POSTFIX = "ActionPerformed";

}
