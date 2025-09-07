package com.kyu.kyucore.fabric;

import com.kyu.kyucore.fabric.events.WorldEvents;
import net.fabricmc.api.ModInitializer;
import com.kyu.kyucore.KyuCore;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;

public final class KyuCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerWorldEvents.LOAD.register(new WorldEvents.OnLoad());
        KyuCore.init();
    }
}
