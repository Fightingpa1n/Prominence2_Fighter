package net.fightingpainter.providence.main.items;

import net.minecraft.item.Item;

import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.items.tools.GreenEnergySpear;
import net.fightingpainter.providence.main.util.Register;

public class ModItems {

    public static Item GE_SPEAR;


    public static void registerItems() { //Register Items
        Main.LOGGER.info("Registering Items..."); 

        GE_SPEAR = Register.registerItem("green_energy_spear", new GreenEnergySpear());

    }

    //private static float attackSpeed(float speed) {return speed - 4f;}
    //private static float baseAttackDamage(float damage) {return damage - 1.0F;}
    //private static int baseAttackDamage(int damage) {return damage - 1;}
}