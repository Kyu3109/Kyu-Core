package com.kyu.kyucore.config;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import com.kyu.kyucore.data.DataMain;
import com.kyu.kyucore.data.DataThread;

import java.io.File;

public class Config {
    protected String mod_id;
    private final File configFile;
    protected JsonObject json = new JsonObject();

    public Config(String mod_id){
        this.configFile = new File("./config", mod_id+".json");
    }

    public Config readConfig(){
        JsonObject jsonObject = DataMain.readFileSync(configFile);

        if(jsonObject == null){
            this.json = new JsonObject();
        }
        else{
            this.json = jsonObject;
        }

        return this;
    }

    public void save(){
        KyuCore.DATA_THREAD.setWriteFile(configFile, json.toString());
    }

    public String set(String key, String defaultValue){
        if(!this.json.has(key)){
            this.json.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return this.json.get(key).getAsString();
    }

    public int set(String key, int defaultValue){
        if(!this.json.has(key)){
            this.json.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return this.json.get(key).getAsInt();
    }

    public boolean set(String key, boolean defaultValue){
        if(!this.json.has(key)){
            this.json.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return this.json.get(key).getAsBoolean();
    }

    public void write(String key, String value){
        this.json.addProperty(key, value);
        save();
    }

    public void write(String key, int value){
        this.json.addProperty(key, value);
        save();
    }

    public void write(String key, boolean value){
        this.json.addProperty(key, value);
        save();
    }

    public Category addCategory(String key){
        if(this.json.has(key)){
            save();
            return new Category(this.json, configFile, key);
        }

        JsonObject newJson = new JsonObject();
        this.json.add(key, newJson);
        save();
        return new Category(this.json, configFile, key);
    }
}
