package com.kyu.kyucore.playerData;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import com.kyu.kyucore.data.DataMain;
import com.kyu.kyucore.data.KyuCoreData;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.File;

public class PlayerData {
    public final ServerPlayerEntity player;
    private final String uuid;
    public JsonObject json;

    public PlayerData(ServerPlayerEntity player){
        this.player = player;
        this.uuid = player.getUuidAsString();

        /*
        KyuCore.DATA.playerData.readFile(this.uuid, (jsonObject -> {
            if(jsonObject != null){
                this.json = jsonObject;
                return;
            }

            this.json = new JsonObject();
            //this.json.addProperty("koins", 0);
        }));*/
        this.json = DataMain.readFileSync(new File(KyuCore.DATA.playerData.dataFolder, this.uuid));
        if(this.json == null){
            this.json = new JsonObject();
        }
    }

    public void save(){
        KyuCore.DATA.playerData.writeFile(this.uuid, json);
    }

    public boolean hasKey(String key){
        return json.has(key);
    }

    public void set(String key, String value){
        json.addProperty(key, value);
        save();
    }

    public void set(String key, int value){
        json.addProperty(key, value);
        save();
    }

    public String getString(String key){
        if(!json.has(key)){
            return "";
        }

        return json.get(key).getAsString();
    }

    public int getInt(String key){
        if(!json.has(key)){
            return 0;
        }

        return json.get(key).getAsInt();
    }
}
