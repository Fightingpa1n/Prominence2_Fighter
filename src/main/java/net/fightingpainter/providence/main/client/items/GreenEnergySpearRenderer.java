package net.fightingpainter.providence.main.client.items;

import net.fightingpainter.providence.main.Main;
import net.fightingpainter.providence.main.items.tools.GreenEnergySpear;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GreenEnergySpearRenderer extends GeoItemRenderer<GreenEnergySpear> {
    public GreenEnergySpearRenderer() {
       //super(new GreenEnergySpearModel());
       super(new DefaultedItemGeoModel<>(new Identifier(Main.MOD_ID, "green_energy_spear")));

       
    }
}