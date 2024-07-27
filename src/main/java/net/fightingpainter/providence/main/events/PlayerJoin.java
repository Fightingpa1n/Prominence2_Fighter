package net.fightingpainter.providence.main.events;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fightingpainter.providence.main.Main;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerJoin {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register(new ServerPlayConnectionEvents.Join() {

            @Override
            public void onPlayReady(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) { //This code runs when a player joins the world or server
                ServerPlayerEntity player = handler.player;
                String playerName = player.getName().getString();

                //Player Register (hardcoded since I'm too lazy to use config files)
                Main.LOGGER.info("Player "+playerName+"("+player.getUuidAsString()+")joined the server");
            }
        });
    }
}
