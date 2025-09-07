package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DataMain {
    public static JsonObject readFileSync(File read){
        try{
            if(!read.exists()){
                System.out.println(read.getName());
                return null;
            }

            String content = new Scanner(read).next();
            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFileSync(File write, String data){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(write))){
            writer.write(data);
            System.out.println("Successfully write file "+ write.getName());
        }catch (Exception e){
            System.out.println("Error write file: " + write.getName());
        }
    }
}
