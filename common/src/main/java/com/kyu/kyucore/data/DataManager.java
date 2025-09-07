package com.kyu.kyucore.data;

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
        System.out.println(worldName);
        dataHome = new File(worldName+"/kyuData");

        if(!dataHome.exists()){
            boolean data = dataHome.mkdirs();
        }

        for(Data data : dataList){
            data.setupData();
        }
    }
}
