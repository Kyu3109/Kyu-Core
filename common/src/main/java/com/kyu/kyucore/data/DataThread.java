package com.kyu.kyucore.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

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

            String content = new Scanner(read.file).next();
            JsonParser parser = new JsonParser();
            return (JsonObject) parser.parse(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(Write write){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(write.file))){
                writer.write(write.data);
                System.out.println("Successfully write file "+ write.file.getName());
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

        public Write(File file, String data){
            this.file = file;
            this.data = data;
        }
    }
}
