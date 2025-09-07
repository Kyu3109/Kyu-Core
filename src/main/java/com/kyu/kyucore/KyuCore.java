package com.kyu.kyucore;

import com.google.gson.JsonObject;
import com.kyu.kyucore.api.data.SetupedDataEvent;
import com.kyu.kyucore.commands.RegisterCommand;
import com.kyu.kyucore.data.Data;
import com.kyu.kyucore.data.DataManager;
import com.kyu.kyucore.data.DataThread;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(modid = KyuCore.MODID, name = KyuCore.NAME, version = KyuCore.VERSION)
public class KyuCore
{
    public static final String MODID = "kyucore";
    public static final String NAME = "Kyu Core";
    public static final String VERSION = "0.2";
    public static final DataThread DATA_THREAD = new DataThread();

    public static final Data data = DataManager.newData(MODID);

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new SetupedDataEvent());
        logger = event.getModLog();
    }

    @EventHandler
    public void serverStarted(FMLServerStartingEvent event){
        DataManager.serverStarting();
        RegisterCommand.register(event);
    }

    public static void testWrite(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Teste", 10);
        data.writeFile("testeWrite", jsonObject);
    }

    public static void testRead(){
        CompletableFuture<JsonObject> future = data.readFile("testeWrite");
        future.thenAccept(json -> {
            System.out.println(json.toString());
        });
    }
}
