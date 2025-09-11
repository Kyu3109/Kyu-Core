package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Data {
    public final String modId;
    public File dataFolder;
    public List<SubFolder> subFolders = new ArrayList<>();

    public Data(String modId){
        this.modId = modId;
    }

    public void setupData(){
        this.dataFolder = new File(DataManager.dataHome, modId);

        if(!this.dataFolder.exists()){
            boolean dir = this.dataFolder.mkdirs();
        }

        for(SubFolder subFolder : subFolders){
            subFolder.setup(this.dataFolder);
        }
    }

    public void writeFile(String fileName, JsonObject data){
        boolean compress = KyuCore.CONFIG.compressFile;
        File file = new File(this.dataFolder, fileName+".dat");
        KyuCore.DATA_THREAD.setWriteFile(file, data.toString(), compress);
    }

    public void readFile(String fileName, ReadFuture callback){
        File file = new File(this.dataFolder, fileName+".dat");
        CompletableFuture<JsonObject> completableFuture = KyuCore.DATA_THREAD.setReadFile(file);

        completableFuture.thenAcceptAsync(callback::onFuture);
    }

    public SubFolder createSubFolder(String name){
        SubFolder subFolder = new SubFolder(name, this.dataFolder);
        subFolders.add(subFolder);
        return subFolder;
    }

    public interface ReadFuture{
        void onFuture(JsonObject json);
    }
}
