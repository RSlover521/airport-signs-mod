package com.rslover521.airportsignsmod.blockentity;

import com.rslover521.airportsignsmod.AirportSignsMod;
import com.rslover521.airportsignsmod.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RunwaySignBlockEntity extends BlockEntity {
    private String runway = "";

    public RunwaySignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RUNWAY_SIGN.get(), pos, state);
    }

    public String getRunway() {
        return runway;
    }

    public void setRunway(String code) {
        this.runway = code == null ? "" : code;
        setChanged();

        if (level != null && !level.isClientSide) {
            AirportSignsMod.LOGGER.debug("Updated RunwaySign at " + worldPosition + " to: " + runway);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.runway = tag.getString("Runway");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putString("Runway", runway);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Runway", runway);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.runway = tag.getString("Runway");
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
