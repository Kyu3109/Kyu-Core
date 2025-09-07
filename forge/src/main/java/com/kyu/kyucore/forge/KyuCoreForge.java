package com.kyu.kyucore.forge;

import com.kyu.kyucore.data.DataManager;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.kyu.kyucore.KyuCore;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(KyuCore.MOD_ID)
public final class KyuCoreForge {
    public KyuCoreForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(KyuCore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        KyuCore.init();
    }

    @SubscribeEvent
    public static void serverLoad(FMLDedicatedServerSetupEvent event){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        DataManager.serverStarting(server.getSavePath(WorldSavePath.ROOT).toAbsolutePath().toString());
    }
}
