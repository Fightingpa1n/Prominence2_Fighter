package net.fightingpainter.providence.main;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eliotlash.mclib.math.functions.classic.Mod;

import net.fightingpainter.providence.main.items.ModItems;
import net.fightingpainter.providence.main.util.ModRegister;
import net.fightingpainter.providence.main.util.Register;
import net.fightingpainter.providence.main.blocks.ModBlocks;
import net.fightingpainter.providence.main.entities.ModEntities;

public class Main implements ModInitializer {
	public static final String MOD_ID = "providence";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Providence Mod...");

		ModEntities.registerEntities(); //register entities
		ModEntities.registerSpawnEggs(); //register spawn eggs
		
		ModItems.registerItems(); //register items
		ModBlocks.registerBlocks(); //register blocks
		Register.registerItemGroup(); //register item group
		
		ModRegister.registerEvents(); //register events
		ModRegister.registerCommands(); //register commands
	}
}