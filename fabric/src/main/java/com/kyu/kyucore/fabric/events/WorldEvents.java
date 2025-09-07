package com.kyu.kyucore.fabric.events;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import com.kyu.kyucore.data.DataManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;

public class WorldEvents{
    public static class OnLoad implements ServerWorldEvents.Load{
        @Override
        public void onWorldLoad(MinecraftServer server, ServerWorld world) {
            DataManager.serverStarting(world.getServer().getSavePath(WorldSavePath.ROOT).toAbsolutePath().toString());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("teste", 10);
            KyuCore.DATA.writeFile("arquivo", jsonObject);

            KyuCore.DATA.readFile("arquivo", (json -> {
                System.out.println(json.toString());
            }));
        }
    }
}
