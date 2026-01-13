package com.rslover521.airportassetsmod.menu;

import com.rslover521.airportassetsmod.blockentity.RunwaySignBlockEntity;
import com.rslover521.airportassetsmod.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import java.util.regex.Pattern;

public class RunwaySignMenu extends AbstractContainerMenu {

    private static final Pattern VALID =
            Pattern.compile("^(0[1-9]|[1-3][0-9])(L|R|C)?$");

    private final BlockPos pos;

    public RunwaySignMenu(int id, Inventory inv, BlockPos pos) {
        super(ModMenus.RUNWAY_SIGN.get(), id);
        this.pos = pos;
    }

    public static RunwaySignMenu fromNetwork(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RunwaySignMenu(id, inv, buf.readBlockPos());
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setRunway(Player player, String text) {
        if (player.level().isClientSide) return;
        if (!VALID.matcher(text).matches()) return;

        if (player.level().getBlockEntity(pos) instanceof RunwaySignBlockEntity sign) {
            sign.setRunway(text);
        }
    }

    /* ---------------- REQUIRED OVERRIDES ---------------- */

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}
