package net.fightingpainter.providence.main.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.events.*;

public class ModRegister {

    //in here we'll put all the register methods that wouln't need a Mod... class 

    

    public static void registerCommands() {
        Main.LOGGER.info("Registering Commands...");
        
        //CommandRegistrationCallback.EVENT.register();
    }

    public static void registerEvents() {
        Main.LOGGER.info("Registering Events...");

        PlayerJoin.register();
    }
    
}
