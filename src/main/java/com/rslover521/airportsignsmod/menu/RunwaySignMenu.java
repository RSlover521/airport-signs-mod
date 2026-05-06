package com.rslover521.airportsignsmod.menu;

import com.rslover521.airportsignsmod.blockentity.RunwaySignBlockEntity;
import com.rslover521.airportsignsmod.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class RunwaySignMenu extends AbstractContainerMenu {
    private final RunwaySignBlockEntity blockEntity;

    public RunwaySignMenu(int id, Inventory inv, RunwaySignBlockEntity be) {
        super(ModMenus.RUNWAY_SIGN.get(), id);
        this.blockEntity = be;
    }

    // Factory method for network
    public static RunwaySignMenu fromNetwork(int id, Inventory inv, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        RunwaySignBlockEntity be = (RunwaySignBlockEntity) inv.player.level().getBlockEntity(pos);
        return new RunwaySignMenu(id, inv, be);
    }

    public RunwaySignBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity != null
            && player.distanceToSqr(
                blockEntity.getBlockPos().getX() + 0.5,
                blockEntity.getBlockPos().getY() + 0.5,
                blockEntity.getBlockPos().getZ() + 0.5
            ) <= 64;
    }

}
