package net.fightingpainter.providence.main.util.mixin;

import net.minecraft.nbt.NbtCompound;

public interface PlayerDataAccessor {
    NbtCompound getPersistentData();
}
