package net.fightingpainter.providence.main.util;

import net.fightingpainter.help.Dict;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.WorldSavePath;
import java.nio.file.Path;
import java.nio.file.Files;

public class DataManager {
    //this is the DataManager utility class, it handles creating, reading, updating, and deleting data in the world/data folder for this mod

    private static Path dataPath; //data directory path
    
    private static Path playersFile; //players.json file path
    
    //===============PRIVATE===============\\
    private static boolean fileExists(Path path) { //check if file exists
        try {return Files.exists(path);} //return if file exists
        catch (Exception e) {e.printStackTrace(); return false;} //print error and return false
    }

    private static void createFolder(Path path) { //create folder
        try {Files.createDirectories(path);} //create folder
        catch (Exception e) {e.printStackTrace();} //print error
    }

    private static Dict readFile(Path path) { //read file
        try {
            String data = Files.readString(path); //read file
            return new Dict(data); //parse json
        }
        catch (Exception e) {e.printStackTrace(); return new Dict();} //print error and return empty dict
    }

    private static void writeFile(Path path, Dict data) { //write file
        try {Files.write(path, data.toJsonString().getBytes());} //write data
        catch (Exception e) {e.printStackTrace();} //print error
    }

    //===============INIT===============\\
    public static void loadData(MinecraftServer server) { //load folders and important files (gets called in the OnWorldLoad mixin)
        dataPath = server.getSavePath(WorldSavePath.ROOT).resolve("data/providence"); //get path
        playersFile = dataPath.resolve("players.json"); //get path

        //folders
        if (!fileExists(dataPath)) {createFolder(dataPath);} //mainFolder

        //files
        if (!fileExists(playersFile)) {writeFile(playersFile, new Dict());}//playerData
    }

    //===============MAIN===============\\
    public static Dict getPlayerData(String playerUUID) { //get player data
        Dict playerData = readFile(playersFile); //read file
        if (playerData.contains(playerUUID)) { //if data exists
            return (Dict) playerData.get(playerUUID); //return player data
        }else { //no data exists
            return new Dict(); //return empty dict
        }
    }

    public static void setPlayerData(String playerUUID, Dict data) { //set player data
        Dict playerData = readFile(playersFile); //read file
        playerData.set(playerUUID, data); //set player data
        writeFile(playersFile, playerData); //write file
    }    
}
