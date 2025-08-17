package com.kyu.kyucore.data;

import com.kyu.kyucore.KyuCore;
import net.minecraft.nbt.NBTTagCompound;
import java.io.File;
import java.util.concurrent.CompletableFuture;

public class Data {
    public final String modId;
    public File dataFolder;

    public Data(String modId){
        this.modId = modId;
    }

    public void setup(){
        this.dataFolder = new File(DataManager.dataHome, modId);

        if(!this.dataFolder.exists()){
            this.dataFolder.mkdirs();
        }
    }

    public void writeFile(String fileName, NBTTagCompound nbt){
        File file = new File(this.dataFolder, fileName+".dat");
        KyuCore.DATA_THREAD.setWriteFile(file, nbt);
    }

    public CompletableFuture<NBTTagCompound> readFile(String fileName){
        File file = new File(this.dataFolder, fileName+".dat");
        return KyuCore.DATA_THREAD.setReadFile(file);
    }
}
