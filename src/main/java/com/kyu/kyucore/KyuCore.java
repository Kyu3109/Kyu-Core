package com.kyu.kyucore;

import com.kyu.kyucore.data.DataManager;
import com.kyu.kyucore.data.DataThread;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Mod(modid = KyuCore.MODID, name = KyuCore.NAME, version = KyuCore.VERSION)
public class KyuCore
{
    public static final String MODID = "kyucore";
    public static final String NAME = "Kyu Core";
    public static final String VERSION = "1.0";
    public static final DataThread dataThread = new DataThread();

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        test();
    }

    private void test(){
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("test", 0);

        DataManager dataManager = new DataManager(KyuCore.MODID);
        dataManager.writeFile("bah", nbt);

        CompletableFuture<NBTTagCompound> future = dataManager.readFile("bah");

        future.thenAccept(nbtData -> {
           System.out.println("key bah tem o valor: " + nbtData.getInteger("bah"));
        });
    }
}
