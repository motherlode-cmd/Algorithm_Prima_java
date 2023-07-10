package com.example.demo;

public abstract class Observable {
    protected Observer obs;

    public void notify(Level level, String mes) {
        obs.update(level, mes);
    }

    public void setObserver(Observer obs) {
        this.obs = obs;
    }

    public Observer getObserver() {
        return obs;
    }
}
