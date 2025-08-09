package com.kyu.kyucore.data;

import com.kyu.kyucore.KyuCore;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import java.io.File;
import java.util.concurrent.CompletableFuture;

public class DataManager {
    public File dataHome = new File("kyu");
    public final File dataFolder;

    public DataManager(String modid){
        this.dataFolder = new File(dataHome, modid);

        if(!dataHome.exists()){
            dataHome.mkdirs();
        }

        if(!this.dataFolder.exists()){
            this.dataFolder.mkdirs();
        }
    }

    public void writeFile(String fileName, NBTTagCompound nbt){
        File file = new File(this.dataFolder, fileName+".dat");
        KyuCore.dataThread.setWriteFile(file, nbt);
    }

    public CompletableFuture<NBTTagCompound> readFile(String fileName){
        File file = new File(this.dataFolder, fileName+".dat");
        return KyuCore.dataThread.setReadFile(file);
    }
}
