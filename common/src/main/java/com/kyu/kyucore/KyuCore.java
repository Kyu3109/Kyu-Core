package com.kyu.kyucore;

import com.kyu.kyucore.config.KyuCoreConfig;
import com.kyu.kyucore.data.DataThread;
import com.kyu.kyucore.data.KyuCoreData;
import com.kyu.kyucore.playerData.PlayerData;
import com.kyu.kyucore.playerData.PlayerDataManager;
import com.kyu.kyucore.tick.TickEventBase;
import com.kyu.kyucore.tick.TickHandler;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.MinecraftServer;

public final class KyuCore {
    public static final String MOD_ID = "kyucore";
    public static KyuCoreConfig CONFIG;
    public static final DataThread DATA_THREAD = new DataThread();
    public static final KyuCoreData DATA = new KyuCoreData();

    public static void init() {
        CONFIG = new KyuCoreConfig();
        setupEvents();
    }

    public static void setupEvents(){
        PlayerEvent.PLAYER_JOIN.register((player) -> {
            PlayerData playerData = PlayerDataManager.playerJoin(player);
        });

        PlayerEvent.PLAYER_QUIT.register(PlayerDataManager::playerLeft);
        TickEvent.SERVER_PRE.register(TickHandler::tick);
    }
}
