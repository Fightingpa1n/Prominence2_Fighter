package net.fightingpainter.providence.main.util;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fightingpainter.providence.main.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class EventListenerThingy {

    public static void register() {
        Main.LOGGER.info("Registering events...");
        register_onPlayerJoin(); // Register the onPlayerJoin event
    }


    private static void register_onPlayerJoin() {
        ServerPlayConnectionEvents.JOIN.register(new ServerPlayConnectionEvents.Join() {

            @Override
            public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) { //This code runs when a player joins the world or server
                ServerPlayerEntity player = handler.player;
                String playerName = player.getName().getString();

                //Player Register (hardcoded since I'm too lazy to use config files)

                if (playerName == "Fightingpainter") {
                    if(!PowerDataHelper.hasPower(player)) {
                        PowerDataHelper.setPlayerPowerId(player, 1);
                    }
                }
                else if (playerName == "KittyLana_Fini") {
                    if(!PowerDataHelper.hasPower(player)) {
                        PowerDataHelper.setPlayerPowerId(player, 2);
                    }
                }
                else if (playerName == "Ultracrafter1000") {
                    if(!PowerDataHelper.hasPower(player)) {
                        PowerDataHelper.setPlayerPowerId(player, 3);
                    }
                }
                else if (playerName == "ELIAS_CocaCola") {
                    if(!PowerDataHelper.hasPower(player)) {
                        PowerDataHelper.setPlayerPowerId(player, 4);
                    }
                }
                
            }
        });
    }
}