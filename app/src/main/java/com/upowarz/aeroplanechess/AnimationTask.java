package com.upowarz.aeroplanechess;

public abstract class AnimationTask {

    private final int waitTime;

    public AnimationTask(int waitTime) {
        this.waitTime = waitTime;
    }

    public abstract void execute();

    public int getWaitTime() {
        return this.waitTime;
    }


}
