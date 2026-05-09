package com.kyu.kyucore.neoforge;

import net.neoforged.fml.common.Mod;

import com.kyu.kyucore.KyuCore;

@Mod(KyuCore.MOD_ID)
public final class KyuCoreNeoForge {
    public KyuCoreNeoForge() {
        // Run our common setup.
        KyuCore.init();
    }
}
