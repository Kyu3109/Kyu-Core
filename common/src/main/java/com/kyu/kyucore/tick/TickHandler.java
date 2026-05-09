package com.kyu.kyucore.tick;

import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.List;

public class TickHandler{
    private static final List<TickEventBase> listeners = new ArrayList<>();

    public static void tick(MinecraftServer server) {
        listeners.forEach(tickEvent -> {
            tickEvent.call(server);
            if(tickEvent.end){
                listeners.remove(tickEvent);
            }
        });
    }

    public static void addListener(TickEventBase tick){
        listeners.add(tick);
    }
}
