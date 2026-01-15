package com.rslover521.airportassetsmod.menu;

import com.rslover521.airportassetsmod.blockentity.RunwaySignBlockEntity;
import com.rslover521.airportassetsmod.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.IContainerFactory;

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
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
