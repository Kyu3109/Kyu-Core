package com.kyu.kyucore.koins;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerKoin {
    public final EntityPlayer player;

    public PlayerKoin(EntityPlayer player){
        this.player = player;
    }

    public int getKoins(){
        if(!player.getEntityData().hasKey("koins")){
            player.getEntityData().setInteger("koins", 0);
            return 0;
        }

        return player.getEntityData().getInteger("koins");
    }

    public void addKoins(int amount){
        int koins = getKoins();
        player.getEntityData().setInteger("koins", koins+(amount*100));
    }

    public void removeKoins(int amount){
        int koins = getKoins();
        koins-=(amount*100);
        if(koins < 0){
            koins = 0;
        }

        this.player.getEntityData().setInteger("koins", koins);
    }
}
