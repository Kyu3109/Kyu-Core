package com.kyu.kyucore.commands;

import com.kyu.kyucore.koins.PlayerKoin;
import com.kyu.kyucore.utils.MessageToPlayer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import javax.annotation.Nonnull;

public class AddCoinCommand extends CommandBase {
    @Nonnull
    @Override
    public String getName(){
        return "addcoin";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender){
        return "/addcoin <?player> <amount>";
    }

    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if(!sender.canUseCommand(2, getName())){
            throw new CommandException("You do not have permission");
        }

        if(args.length == 1){
            if(!(sender instanceof EntityPlayerMP)){
                return;
            }

            EntityPlayerMP player = (EntityPlayerMP) sender.getCommandSenderEntity();

            add(args[0], player, player);
        }

        else if(args.length >= 2){
            PlayerList players = server.getPlayerList();
            EntityPlayerMP playerTarget = players.getPlayerByUsername(args[0]);
            if(playerTarget == null){
                if(!(sender instanceof EntityPlayer)){
                    return;
                }
                MessageToPlayer.red(getName(), "Invalid Player", (EntityPlayerMP) sender.getCommandSenderEntity());
                return;
            }

            add(args[1], playerTarget, sender.getCommandSenderEntity());
        }
    }

    private void add(String amountString, EntityPlayerMP player, Entity sender){
        try{
            int amount = parseInt(amountString);
            PlayerKoin bal = new PlayerKoin(player);
            bal.addKoins(amount);

        } catch (NumberInvalidException e) {
            if(!(sender instanceof EntityPlayer)){ return; }
            MessageToPlayer.red(getName(), "Invalid number amount", (EntityPlayer) sender);
        }
    }
}
