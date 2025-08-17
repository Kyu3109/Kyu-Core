package com.kyu.kyucore.ticks;

import com.kyu.kyucore.KyuCore;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = KyuCore.MODID)
public class TickHandler {
    private static final List<TickEventBase> listeners = new ArrayList<>();
    private static final List<TickEventBase> clientListeners = new ArrayList<>();

    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event){
        listeners.forEach(tickEvent -> {
            tickEvent.call();
            if(tickEvent.end){
                listeners.remove(tickEvent);
            }
        });
    }

    @SubscribeEvent
    public static void ClientOnTick(TickEvent.ClientTickEvent event){
        clientListeners.forEach(tickEvent -> {
            tickEvent.call();
            if(tickEvent.end){
                listeners.remove(tickEvent);
            }
        });
    }

    public static void addListener(TickEventBase listener, Side side){
        if(side.isServer()){ listeners.add(listener); }
        else{ clientListeners.add(listener); }
    }
}
