package com.kyu.kyucore;

import com.kyu.kyucore.config.KyuCoreConfig;
import com.kyu.kyucore.data.Data;
import com.kyu.kyucore.data.DataManager;
import com.kyu.kyucore.data.DataThread;

public final class KyuCore {
    public static final String MOD_ID = "kyucore";
    public static KyuCoreConfig CONFIG;
    public static final DataThread DATA_THREAD = new DataThread();
    public static final Data DATA = DataManager.newData(MOD_ID);

    public static void init() {
        CONFIG = new KyuCoreConfig();
    }
}
