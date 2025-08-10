package com.kyu.kyucore.data;

import com.kyu.kyucore.KyuCore;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = KyuCore.MODID)
public class DataManager {
    public static File dataHome;
    public static List<Data> dataList = new ArrayList<>();


    public static Data newData(String modid){
        Data data = new Data(modid);
        dataList.add(data);
        return data;
    }

    public static void serverStarting(){
        dataHome = new File("./saves/"+ FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName()+"/kyuData");

        if(!dataHome.exists()){
            dataHome.mkdirs();
        }

        for(Data data : dataList){
            data.setup();
        }
    }
}
