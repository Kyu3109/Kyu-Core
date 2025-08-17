package com.kyu.kyucore.ticks;

public abstract class TickEventBase {
    public boolean end = false;
    private final int tickCooldown;
    private int tick;

    public TickEventBase(int tickCooldown) {
        this.tickCooldown = tickCooldown;
    }

    public void call() {
        if (tickCooldown == tick) {
            tick = 0;
            execute();
        }
        tick++;
    }

    public abstract void execute();
}
