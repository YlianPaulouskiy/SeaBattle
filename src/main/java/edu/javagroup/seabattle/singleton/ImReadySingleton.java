package edu.javagroup.seabattle.singleton;


public class ImReadySingleton {

    private static ImReadySingleton instance;
    private final boolean ready;

    private ImReadySingleton(boolean ready) {
        this.ready = ready;
    }

    public static ImReadySingleton instance(Boolean ready) {
        if (instance == null) {
            instance = new ImReadySingleton(false);
        }
        if (ready != null) {
            instance = new ImReadySingleton(ready);
        }
        return instance;
    }

    public Boolean imReady() {
        return ready;
    }
}
