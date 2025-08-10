package com.kyu.kyucore;

import com.kyu.kyucore.commands.RegisterCommand;
import com.kyu.kyucore.data.Data;
import com.kyu.kyucore.data.DataManager;
import com.kyu.kyucore.data.DataThread;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = KyuCore.MODID, name = KyuCore.NAME, version = KyuCore.VERSION)
public class KyuCore
{
    public static final String MODID = "kyucore";
    public static final String NAME = "Kyu Core";
    public static final String VERSION = "1.0";
    public static final DataThread DATA_THREAD = new DataThread();

    public static final Data data = DataManager.newData(MODID);

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void serverStarted(FMLServerStartingEvent event){
        DataManager.serverStarting();
        RegisterCommand.register(event);
    }
}
