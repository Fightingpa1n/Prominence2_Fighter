package net.fightingpainter.providence.main.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.fightingpainter.providence.main.util.PowerDataHelper;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.nbt.NbtCompound;

import static net.minecraft.server.command.CommandManager.literal;

public class PowerCommand {

    public static void registerMeSenpai(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("power")
            .then(CommandManager.argument("target", EntityArgumentType.player())
                .then(literal("get")
                    .executes(ctx -> run_getPower(ctx, EntityArgumentType.getPlayer(ctx, "target")))
                )
                .then(literal("set")
                    .then(CommandManager.argument("id", IntegerArgumentType.integer(0, 4))
                        .executes(ctx -> run_setPower(ctx, EntityArgumentType.getPlayer(ctx, "target"), IntegerArgumentType.getInteger(ctx, "id")))
                    )
                )
            )
        );

        dispatcher.register(literal("power")
            .then(literal("get")
                .executes(ctx -> run_getPower(ctx, ctx.getSource().getPlayer()))
            )
            .then(literal("set")
                .then(CommandManager.argument("id", IntegerArgumentType.integer(0, 4))
                    .executes(ctx -> run_setPower(ctx, ctx.getSource().getPlayer(), IntegerArgumentType.getInteger(ctx, "id")))
                )
            )
        );
    }

    private static int run_getPower(CommandContext<ServerCommandSource> context, ServerPlayerEntity player) {
        if (player != null) {
            NbtCompound power_data = PowerDataHelper.getPlayerPower(player);
            context.getSource().sendFeedback(() -> Text.of("Power Data: " + power_data), false);

            return 1;
        } else {
            context.getSource().sendError(Text.literal("Player not found"));
            return 0;
        }
    }

    private static int run_setPower(CommandContext<ServerCommandSource> context, ServerPlayerEntity player, int power_id) {
        if (player != null) {
            
            PowerDataHelper.setPlayerPowerId(player, power_id);
            context.getSource().sendFeedback(() -> Text.of("Power set to: " + power_id), true);

            return 1;
        } else {
            context.getSource().sendError(Text.literal("Player not found"));
            return 0;
        }
    }
}
