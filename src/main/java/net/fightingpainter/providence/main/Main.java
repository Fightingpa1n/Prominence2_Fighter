package net.fightingpainter.providence.main;

import net.fabricmc.api.ModInitializer;
import net.fightingpainter.providence.main.util.EventListenerThingy;
import net.fightingpainter.providence.main.util.RegisterThingyIdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main implements ModInitializer {

	public static final String MOD_ID = "fightingpainter"; // Define the mod id
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID); // Create the logger thingy

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		
		RegisterThingyIdk.registerCommands();
		
		EventListenerThingy.register();
	}
}