package net.fightingpainter.providence.main.client.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.entities.OwlEntity;

@Environment(EnvType.CLIENT)
public class OwlEntityRenderer extends GeoEntityRenderer<OwlEntity> {
    public OwlEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(new Identifier(Main.MOD_ID, "owl"), true));
    }
}