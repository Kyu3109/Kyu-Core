package com.kyu.kyucore.config;

import com.google.gson.JsonObject;
import com.kyu.kyucore.KyuCore;
import java.io.File;

public class Category{
    private final String categoryKey;
    private final File configFile;
    private final JsonObject json;

    public Category(JsonObject jsonObject, File config, String categoryKey){
        this.configFile = config;
        this.json = jsonObject;
        this.categoryKey = categoryKey;
    }

    public void save(){
        KyuCore.DATA_THREAD.setWriteFile(configFile, json.toString());
    }

    public String set(String key, String defaultValue){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        if(!category.has(key)){
            category.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return category.get(key).getAsString();
    }

    public int set(String key, int defaultValue){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        if(!category.has(key)){
            category.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return category.get(key).getAsInt();
    }

    public boolean set(String key, boolean defaultValue){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        if(!category.has(key)){
            category.addProperty(key, defaultValue);
            save();
            return defaultValue;
        }

        save();
        return category.get(key).getAsBoolean();
    }

    public void write(String key, String value){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        category.addProperty(key, value);
        save();
    }

    public void write(String key, int value){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        category.addProperty(key, value);
        save();
    }

    public void write(String key, boolean value){
        JsonObject category = this.json.getAsJsonObject(categoryKey);

        this.json.addProperty(key, value);
        save();
    }
}