package net.fightingpainter.providence.main.client.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.fightingpainter.providence.main.Client;
import net.fightingpainter.providence.main.entities.ModEntities;

@Environment(EnvType.CLIENT)
public class ClientModEntities {

    public static void registerEntityRenderers(){
        Client.LOGGER.info("Registering entity renderers...");

        EntityRendererRegistry.register(ModEntities.OWL, OwlEntityRenderer::new);
    }    
}
