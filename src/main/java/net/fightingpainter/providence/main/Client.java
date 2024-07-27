package net.fightingpainter.providence.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fightingpainter.providence.main.client.entities.ClientModEntities;
import net.fightingpainter.providence.main.client.handlers.KeyHandler;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.MOD_ID+"[client]"); // a seperate logger for the client

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Providence Client...");

        ClientModEntities.registerEntityRenderers();
        KeyHandler.registerKeys();
    }
}
