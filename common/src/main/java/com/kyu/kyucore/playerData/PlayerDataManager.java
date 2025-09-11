package com.kyu.kyucore.playerData;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataManager {
    private static List<PlayerData> players = new ArrayList<>();

    public static PlayerData playerJoin(ServerPlayerEntity player){
        if(playerDataLoaded(player)){ return getPlayerData(player); }

        PlayerData playerData = new PlayerData(player);
        players.add(playerData);
        return playerData;
    }

    public static boolean playerDataLoaded(ServerPlayerEntity player){
        for(PlayerData playerData : players){
            if(playerData.player.getUuid().equals(player.getUuid())){
                return true;
            }
        }

        return false;
    }

    public static PlayerData getPlayerData(ServerPlayerEntity player){
        for(PlayerData playerData : players){
            if(playerData.player.getUuid().equals(player.getUuid())){
                return playerData;
            }
        }

        return playerJoin(player);
    }

    public static void removePlayerByUUID(UUID uuid){
        System.out.println(players);
        players.removeIf(playerData -> playerData.player.getUuid().equals(uuid));
        System.out.println(players);
    }

    public static void playerLeft(ServerPlayerEntity player){
        removePlayerByUUID(player.getUuid());
    }
}
