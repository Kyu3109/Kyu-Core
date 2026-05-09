package com.kyu.kyucore.data;

import com.kyu.kyucore.KyuCore;

public class KyuCoreData{
    public final Data data = DataManager.newData(KyuCore.MOD_ID);
    public final SubFolder playerData = data.createSubFolder("playerData");
}
