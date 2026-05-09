package com.kyu.kyucore.data;

import com.google.gson.*;
import com.kyu.kyucore.utils.LogDebug;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataThread {
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();

    public DataThread(){
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Task task = queue.take();
                    if (task instanceof Read) {
                        JsonObject data = readFile((Read) task);
                        ((Read) task).future.complete(data);
                    } else if (task instanceof Write) {
                        writeFile((Write) task);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "DataThread");

        thread.setDaemon(true);
        thread.start();
    }

    private JsonObject readFile(Read read){
        try{
            if(!read.file.exists()){
                System.out.println(read.file.getName());
                return null;
            }

            if(isCompressed(read.file)){
                JsonObject object = readCompressFile(read.file);
                if(object == null){ return null; }

                return object;
            }

            BufferedReader reader = new BufferedReader(new FileReader(read.file));
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

    private JsonObject readCompressFile(File file){
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

    private void writeFile(Write write){
        try{
            if(!write.compressFile){
                jsonWrite(write.file, write.data);
            }
            else{
                OutputStreamWriter outputStreamWriter = compressFile(write.file);
                assert outputStreamWriter != null;
                BufferedWriter writer = new BufferedWriter(outputStreamWriter);
                writer.write(write.data);
                writer.close();
            }
            LogDebug.printFile(write.file.getName(), write.data);
        }catch (Exception e){
            System.out.println("Error write file: " + write.file.getName());
        }
    }

    public CompletableFuture<JsonObject> setReadFile(File file){
        Read read = new Read(file);
        queue.offer(read);
        return read.future;
    }

    public void setWriteFile(File file, String data){
        Write write = new Write(file, data);
        queue.offer(write);
    }

    public void setWriteFile(File file, String data, boolean compress){
        Write write = new Write(file, data);
        write.setCompressFile(compress);
        queue.offer(write);
    }

    private OutputStreamWriter compressFile(File file){
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(fileOutputStream);
            return new OutputStreamWriter(gzipOutputStream, StandardCharsets.UTF_8);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private void jsonWrite(File file, String json){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(json);
        String formatedJson = gson.toJson(jsonElement);

        try(FileWriter writer = new FileWriter(file)){
            writer.write(formatedJson);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private boolean isCompressed(File file){
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

    private interface Task{}
    private static class Read implements Task{
        public final File file;
        CompletableFuture<JsonObject> future;

        public Read(File file){
            this.file = file;
            this.future = new CompletableFuture<>();
        }
    }

    private static class Write implements Task{
        public final File file;
        public final String data;
        public boolean compressFile = false;

        public Write(File file, String data){
            this.file = file;
            this.data = data;
        }

        public void setCompressFile(boolean compress){
            this.compressFile = compress;
        }
    }
}
