package com.rslover521.airportassetsmod.blockentity;

import com.rslover521.airportassetsmod.menu.RunwaySignMenu;
import com.rslover521.airportassetsmod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RunwaySignBlockEntity extends BlockEntity implements MenuProvider {

    private String runway = "";

    public RunwaySignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RUNWAY_SIGN.get(), pos, state);
    }

    public void setRunway(String code) {
        this.runway = code == null ? "" : code;
        setChanged();

        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getRunway() {
        return runway;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Runway", runway);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        runway = tag.getString("Runway");
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putString("Runway", runway);
        return tag;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.airportassets.runway_sign");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new RunwaySignMenu(id, inv, worldPosition);
    }
}
