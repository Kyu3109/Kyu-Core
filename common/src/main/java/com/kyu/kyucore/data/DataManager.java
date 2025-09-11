package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static File dataHome;
    public static List<Data> dataList = new ArrayList<>();

    public static Data newData(String modid){
        Data data = new Data(modid);
        dataList.add(data);
        return data;
    }

    public static void serverStarting(String worldName){
        dataHome = new File(worldName+"/kyuData");

        if(!dataHome.exists()){
            boolean data = dataHome.mkdirs();
        }

        for(Data data : dataList){
            data.setupData();
        }

        testeCompress();
    }

    private static void testeCompress(){
        JsonObject json = new JsonObject();
        json.addProperty("bah", 10);
        KyuCore.DATA.data.writeFile("compressedFile", json);
    }
}
