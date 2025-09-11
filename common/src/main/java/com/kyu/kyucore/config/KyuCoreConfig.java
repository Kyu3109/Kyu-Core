package com.kyu.kyucore.config;

import com.kyu.kyucore.KyuCore;

public class KyuCoreConfig {
    private final Config config = new Config(KyuCore.MOD_ID).readConfig();

    public Category data = config.addCategory("data");

    public boolean compressFile = data.set("compressFile", true);
    public boolean debugFiles = data.set("debugFiles", false);
    public boolean debugFilesContent = data.set("debugFilesContent", false);
}
