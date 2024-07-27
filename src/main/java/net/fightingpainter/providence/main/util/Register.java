package net.fightingpainter.providence.main.util;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;


public class Register {

    public static final List<Item> ITEMS = new ArrayList<>(); //item list


    public static Item registerItem(String name, Item item) {
        Item iteeem = Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, name), item); //register item
        ITEMS.add(iteeem); //add item to list
        return iteeem; //return item
    }

    public static Block registerBlock(String name, Block block) {
        Block blockyWocky = Registry.register(Registries.BLOCK, new Identifier(Main.MOD_ID, name), block); //register block
        registerItem(name, new BlockItem(blockyWocky, new FabricItemSettings())); //register block item
        return blockyWocky; //return block    
    }

    public static Item registerSpawnEgg(String name, EntityType<? extends MobEntity> entityType, int primaryColor, int secondaryColor) {
        Item spawnEgg = registerItem(name, new SpawnEggItem(entityType, primaryColor, secondaryColor, new FabricItemSettings())); //register spawn egg
        return spawnEgg; //return spawn egg
    }

    public static void registerItemGroup() {
        Main.LOGGER.info("Registering ItemGroup...");
        
        Item ico = ModItems.GE_SPEAR; //icon
        String name = "providence"; //name

        ItemGroup.Builder builder = FabricItemGroup.builder(); //builder

        builder.icon(() -> new ItemStack(ico)); //icon
        builder.displayName(Text.translatable("itemgroup."+Main.MOD_ID));

        builder.entries((displayContext, entries) -> { //adding Items
            for (Item item : ITEMS) {
                entries.add(item);
            }
        });

        Registry.register(Registries.ITEM_GROUP, new Identifier(Main.MOD_ID, name), builder.build()); //register ItemGroup
    }
}
