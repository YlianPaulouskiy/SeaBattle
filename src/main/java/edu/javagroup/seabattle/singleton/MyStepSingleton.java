package edu.javagroup.seabattle.singleton;

public class MyStepSingleton {

    private static MyStepSingleton instance;
    private final boolean step;

    private MyStepSingleton(boolean step) {
        this.step = step;
    }

    public static MyStepSingleton instance(Boolean step) {
        if (instance == null) {
            instance = new MyStepSingleton(true);
        }
        if (step != null) {
            instance = new MyStepSingleton(step);
        }
        return instance;
    }

    public Boolean myStep() {
        return step;
    }

}
