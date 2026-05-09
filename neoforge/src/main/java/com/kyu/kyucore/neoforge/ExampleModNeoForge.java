package com.kyu.kyucore.neoforge;

import net.neoforged.fml.common.Mod;

import com.kyu.kyucore.ExampleMod;

@Mod(ExampleMod.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        ExampleMod.init();
    }
}
