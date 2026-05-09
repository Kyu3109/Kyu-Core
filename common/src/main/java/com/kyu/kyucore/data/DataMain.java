package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class DataMain {
    public static JsonObject readFileSync(File read){
        try{
            if(!read.exists()){
                System.out.println(read.getName());
                return null;
            }

            if(isCompressed(read)){
                JsonObject object = readCompressFile(read);
                if(object == null){ return null; }

                return object;
            }

            BufferedReader reader = new BufferedReader(new FileReader(read));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }

            return JsonParser.parseString(builder.toString()).getAsJsonObject();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JsonObject readCompressFile(File file){
        try {
            GZIPInputStream input = new GZIPInputStream(new FileInputStream(file));
            InputStreamReader inputStreamReader = new InputStreamReader(input, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String content = reader.readLine();
            System.out.println(content);

            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(content);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void writeFileSync(File write, String data){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(write))){
            writer.write(data);
            System.out.println("Successfully write file "+ write.getName());
        }catch (Exception e){
            System.out.println("Error write file: " + write.getName());
        }
    }

    private static boolean isCompressed(File file){
        try(InputStream read = new FileInputStream(file)){
            int byte1 = read.read();
            int byte2 = read.read();
            System.out.println(byte1+"-"+byte2);
            return byte1 == 0x1F && byte2 == 0x8B;
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
}
