package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import java.io.File;
import java.util.concurrent.CompletableFuture;

public class SubFolder{
    public final String folderName;
    public File dataFolder;

    public SubFolder(String name, File dataHome){
        this.folderName = name;
        if(dataHome != null){
            setup(dataHome);
        }
    }

    public void setup(File dataHome){
        this.dataFolder = new File(dataHome, this.folderName);

        if(!this.dataFolder.exists()){
            boolean created = this.dataFolder.mkdirs();
        }
    }

    public void writeFile(String fileName, JsonObject data){
        boolean compress = KyuCore.CONFIG.compressFile;
        File file = new File(this.dataFolder, fileName+".dat");
        KyuCore.DATA_THREAD.setWriteFile(file, data.toString(), compress);
    }

    public void readFile(String fileName, Data.ReadFuture callback){
        File file = new File(this.dataFolder, fileName+".dat");
        CompletableFuture<JsonObject> completableFuture = KyuCore.DATA_THREAD.setReadFile(file);

        completableFuture.thenAcceptAsync(callback::onFuture);
    }
}
