package dev.enderdevelopment.fourthrealm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public final class FourthRealm implements ModInitializer {
    public static final String MOD_ID = "fourth_realm";
    public static final Identifier REALM = Identifier.of(MOD_ID, "aether");
    public static final Identifier OVERWORLD = Identifier.of("minecraft", "overworld");

    @Override
    public void onInitialize() {
        RealmContent.register();
        ServerTickEvents.END_WORLD_TICK.register(FourthRealm::returnFallenPlayers);
        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (!(player instanceof ServerPlayerEntity serverPlayer) || !(entity instanceof LivingEntity target)) return ActionResult.PASS;
            if (!target.getCommandTags().contains(MOD_ID + ".deity")) return ActionResult.PASS;
            ItemStack held = player.getStackInHand(hand);
            if (held.isOf(RealmContent.OATH_SIGIL)) {
                target.addCommandTag(MOD_ID + ".served");
                serverPlayer.sendMessage(Text.literal("The Fourth God accepts your oath. The realm's guardians will not attack you."), true);
                held.decrement(1);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }

    private static void returnFallenPlayers(ServerWorld world) {
        if (!world.getRegistryKey().getValue().equals(REALM)) return;
        for (ServerPlayerEntity player : world.getPlayers()) {
            if (player.getY() >= -32 || player.isRemoved()) continue;
            ServerWorld overworld = world.getServer().getWorld(World.OVERWORLD);
            if (overworld == null) continue;
            player.teleportTo(new TeleportTarget(overworld, new net.minecraft.util.math.Vec3d(player.getX(), 180, player.getZ()),
                    player.getVelocity(), player.getYaw(), player.getPitch(), TeleportTarget.NO_OP));
            player.sendMessage(Text.literal("You fall through the veil and return to the Overworld."), true);
        }
    }
}
