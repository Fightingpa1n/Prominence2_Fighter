package net.fightingpainter.providence.main.client.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import net.fightingpainter.providence.main.client.screens.SkillTree;

public class KeyHandler {
    public static final String CATERGORY = "key.category.providence";

    public static final String SKILLTREE = "key.providence.skilltree";
    public static KeyBinding KEY_SKILLTREE;


    public static void registerKeys() { //register keybinds
        KEY_SKILLTREE = KeyBindingHelper.registerKeyBinding(new KeyBinding(SKILLTREE, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, CATERGORY));

        registerCanonEvemt();        
    }

    public static void registerCanonEvemt() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (KEY_SKILLTREE.wasPressed()) { //skilltree (the keybind was pressed) //IF KEY IS PRESSED
                if (client.player == null) return; //if the player is null, return (meaning if no player was found, it will end the function without doing anything else)

                MinecraftClient.getInstance().setScreen(new SkillTree()); //open the skilltree screen (we get the Instance of the Client aka the Game Window for a player, and set the current screen(what gui is open ex. inventory, pause menu, etc) to the skilltree)
            }
        });
    }
}
