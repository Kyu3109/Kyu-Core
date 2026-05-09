package com.kyu.kyucore.tick;

import net.minecraft.server.MinecraftServer;

public abstract class TickEventBase {
    public boolean end = false;
    private final int tickCooldown;
    private int tick = 0;

    public TickEventBase(int tickCooldown) {
        this.tickCooldown = tickCooldown;
    }

    public void call(MinecraftServer server) {
        if (tickCooldown == tick) {
            tick = 0;
            execute(server);
        }
        tick++;
    }

    public abstract void execute(MinecraftServer server);
}
