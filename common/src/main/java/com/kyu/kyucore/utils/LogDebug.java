package com.kyu.kyucore.utils;

import com.kyu.kyucore.KyuCore;
import com.kyu.kyucore.config.KyuCoreConfig;

public class LogDebug {
    public static void print(boolean debug, String name, String content){
        if(!debug){ return; }

        System.out.printf("%s: %s%n", name, content);
    }

    public static void printFile(String fileName, String content){
        if(!KyuCore.CONFIG.debugFiles){ return; }

        System.out.println("Successfully write file "+ fileName);

        if(KyuCore.CONFIG.debugFilesContent){
            System.out.printf("content:%s%n", content);
        }
    }
}
