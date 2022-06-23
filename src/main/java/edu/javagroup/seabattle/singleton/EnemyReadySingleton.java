package edu.javagroup.seabattle.singleton;


public class EnemyReadySingleton {

    private static EnemyReadySingleton instance;
    private final boolean ready;

    private EnemyReadySingleton(boolean ready) {
        this.ready = ready;
    }

    public static EnemyReadySingleton instance(Boolean ready) {
        if (instance == null) {
            instance = new EnemyReadySingleton(false);
        }
        if (ready != null) {
            instance = new EnemyReadySingleton(ready);
        }
        return instance;
    }

    public Boolean enemyReady() {
        return ready;
    }
}
