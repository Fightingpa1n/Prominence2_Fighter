package net.fightingpainter.providence.main.util;

import net.fabricmc.loader.api.FabricLoader;
import net.fightingpainter.help.Dict;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;

import java.nio.file.Path;
import java.nio.file.Files;

public class FileManager {
    private static final Path configPath = FabricLoader.getInstance().getGameDir().resolve("config/providence");
    
    //so this is the ConfigManager utility class, it is for reading config and writing config files.



}
