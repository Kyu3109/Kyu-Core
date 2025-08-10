package com.kyu.kyucore.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nonnull;

public class RegisterCommand {
    public static void register(FMLServerStartingEvent event){
        event.registerServerCommand(new BalCommand() {
            @Override
            public String getUsage(ICommandSender iCommandSender) {
                return "";
            }
        });
        event.registerServerCommand(new AddCoinCommand());
    }
}
