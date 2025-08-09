package com.kyu.kyucore.data;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

public class DataThread {
    private final BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
    private final Thread thread;

    public DataThread(){
        thread = new Thread(() -> {
            while(true){
                try{
                    Task task = queue.take();
                    if(task instanceof Read){
                        NBTTagCompound nbt = readFile((Read) task);
                        ((Read) task).future.complete(nbt);
                    }
                    else if(task instanceof Write){
                        writeFile((Write) task);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    private NBTTagCompound readFile(Read read){
        try{
            if(!read.file.exists()){
                return null;
            }

            NBTTagCompound nbtData = new NBTTagCompound();
            nbtData = CompressedStreamTools.readCompressed(Files.newInputStream(read.file.toPath()));
            return nbtData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile(Write write){
        try{
            CompressedStreamTools.writeCompressed(write.nbt, Files.newOutputStream(write.file.toPath()));
            System.out.println("successfully write file: " + write.file.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<NBTTagCompound> setReadFile(File file){
        Read read = new Read(file);
        queue.offer(read);
        return read.future;
    }

    public void setWriteFile(File file, NBTTagCompound nbt){
        Write write = new Write(file, nbt);
        queue.offer(write);
    }

    private static interface Task{}
    private static class Read implements Task{
        public final File file;
        CompletableFuture<NBTTagCompound> future;

        public Read(File file){
            this.file = file;
            this.future = new CompletableFuture<>();
        }
    }

    private static class Write implements Task{
        public final File file;
        public final NBTTagCompound nbt;

        public Write(File file, NBTTagCompound nbt){
            this.file = file;
            this.nbt = nbt;
        }
    }
}
