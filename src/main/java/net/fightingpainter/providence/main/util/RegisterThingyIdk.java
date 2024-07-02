package net.fightingpainter.providence.main.util;


import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.commands.*;

public class RegisterThingyIdk {

    //this is the register class that contains register methods for all the things that need to be registered but don't have
    //a more convenient place to be registered like blocks or items for example.

    public static void registerCommands() {
        Main.LOGGER.info("Welcoming the Commands family, and guiding them to their table...");
        CommandRegistrationCallback.EVENT.register(PowerCommand::registerMeSenpai);
    }
    
}
