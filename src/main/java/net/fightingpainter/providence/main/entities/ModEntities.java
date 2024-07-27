package net.fightingpainter.providence.main.entities;

import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.util.Register;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static EntityType<OwlEntity> OWL;
    public static Item OWL_SPAWN_EGG;

    //public static final SpawnEggItem BAT_SPAWN_EGG = registerItem("bat_spawn_egg", new SpawnEggItem(EntityRegistry.BAT, 0x1F1F1F, 0x0D0D0D, new Item.Properties()));

    public static void registerEntities(){
        Main.LOGGER.info("Registering Entities...");
        OWL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Main.MOD_ID, "owl"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OwlEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.9f)).build());
        FabricDefaultAttributeRegistry.register(OWL, OwlEntity.setEntityAtributes());
    }


    public static void registerSpawnEggs(){
        Main.LOGGER.info("Registering Spawn Eggs...");

        OWL_SPAWN_EGG = Register.registerSpawnEgg("owl_spawn_egg", OWL, 0x1F1F1F, 0x0D0D0D);
    }

}