package com.kyu.kyucore.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MessageToPlayer {
    public static void red(String name, String message, EntityPlayer player){
        String msg = "[{"+TextFormatting.DARK_PURPLE+name+"}] "+TextFormatting.RED+message;
        TextComponentString text = new TextComponentString(msg);
        player.sendMessage(text);
    }
}
