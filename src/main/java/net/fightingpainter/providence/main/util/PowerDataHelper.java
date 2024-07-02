package net.fightingpainter.providence.main.util;

import net.fightingpainter.providence.main.util.mixin.PlayerDataAccessor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

public class PowerDataHelper {

    // this is a helper class that should make it easier to access the power data of a player.
    //most methods will therefore require a playerServerEntity(I think that's the right one atleast) as a parameter.

    //powers should be registered somewhere to make sure everything works properly. but for now It will be done manually.
    /* so here is a reference to what id corresponds to what power:
        0 = No power
        1 = Green Shield Energy (idk I'm not good at naming things) [Fightingpainter]
        2 = nothing yet [KittyLana_Fini]
        3 = nothing yet [Ultracrafter1000]
        4 = nothing yet [ELIAS_CocaCola]
    */

    public static NbtCompound getPlayerPower(ServerPlayerEntity player) { //returns all power data from player. (default if no data is found)
        NbtCompound data = ((PlayerDataAccessor) player).getPersistentData();

        if (data.contains("power")) {
            return data.getCompound("power");
        } else {
            ((PlayerDataAccessor) player).getPersistentData().put("power", getDefaultPower());
            return getDefaultPower();
        }
    }

    public static Boolean hasPower(ServerPlayerEntity player) { //returns true if player has power data.
        if (((PlayerDataAccessor) player).getPersistentData().contains("power")) {
            if (getPlayerPower(player).getInt("id") != 0) {
                return true;
            }
        }
        return false;
    }


    // Power ID methods
    public static int getPlayerPowerId(ServerPlayerEntity player) { //returns power id from player.
        return getPlayerPower(player).getInt("id");
    }
    public static void setPlayerPowerId(ServerPlayerEntity player, int powerId) {
        NbtCompound powerData = getPlayerPower(player);
        powerData.putInt("id", powerId);
        ((PlayerDataAccessor) player).getPersistentData().put("power", powerData);
    }


    //default power data
    public static NbtCompound getDefaultPower(){ //returns the default nbt compound | .put("power", PowerDataHelper.getDefault())
        NbtCompound power = new NbtCompound();
        power.putInt("id", 0);
        return power;
    }
}
