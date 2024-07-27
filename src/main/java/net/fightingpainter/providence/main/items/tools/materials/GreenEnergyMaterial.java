package net.fightingpainter.providence.main.items.tools.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class GreenEnergyMaterial implements ToolMaterial {
    private int miningLevel;

    public GreenEnergyMaterial(int miningLevel){
        this.miningLevel = miningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    @Override public int getDurability() {return 0;} //no durability
    @Override public Ingredient getRepairIngredient() {return Ingredient.EMPTY;} //no repair ingredient (since it has no durability)
    @Override public int getEnchantability() {return 0;} //no enchantability

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public float getMiningSpeedMultiplier() {return 0f;}

    @Override
    public float getAttackDamage() {return 0f;}
}
