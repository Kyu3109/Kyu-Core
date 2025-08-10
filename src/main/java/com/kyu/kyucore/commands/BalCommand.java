package com.kyu.kyucore.commands;

import com.kyu.kyucore.koins.PlayerKoin;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public abstract class BalCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName(){
        return "bal";
    }

    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args){
        if(!(sender instanceof EntityPlayer)){
            return;
        }

        EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
        PlayerKoin playerKoin = new PlayerKoin(player);
        int koins = playerKoin.getKoins();
        String koinsString = String.valueOf(koins);


        char[] values = new char[koinsString.length()];
        for(int i=0;i<koinsString.length();i++){
            values[i] = koinsString.charAt(i);
        }

        StringBuilder bal = new StringBuilder();
        if(values.length>=3){
            for(int i=0;i<values.length-2;i++){
                bal.append(values[i]);
            }
            bal.append(".");
            bal.append(values[values.length-1]);
            bal.append(values[values.length-2]);
        }

        else{
            bal.append("0.");
            for(int i=0;i<values.length;i++){
                bal.append(values[i]);
            }
        }

        player.sendMessage(new TextComponentString("[ Bal ] koins: " + bal));
    }
}
