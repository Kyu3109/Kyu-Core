package com.kyu.kyucore.koins;

import com.kyu.kyucore.playerData.PlayerData;
import com.kyu.kyucore.playerData.PlayerDataManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerKoin {
    private final PlayerData playerData;

    public PlayerKoin(ServerPlayerEntity player){
        this.playerData = PlayerDataManager.playerJoin(player);
    }

    public int getKoins(){
        if(!playerData.hasKey("koins")){
            playerData.set("koins", 0);
        }

        return playerData.getInt("koins");
    }

    public void addKoins(int amount){
        int money = getKoins();
        playerData.set("koins", money+(amount*100));
    }

    /*
    public void removeKoins(int amount){
        int koins = getKoins();
        koins-=(amount*100);
        if(koins < 0){
            koins = 0;
        }

        this.player.getEntityData().setInteger("koins", koins);
        playerData.set("koins");
    }*/
}
