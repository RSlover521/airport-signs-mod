package com.rslover521.airportsignsmod.registry;

import com.rslover521.airportsignsmod.AirportSignsMod;
import com.rslover521.airportsignsmod.blocks.ILSCriticalSignBlock;
import com.rslover521.airportsignsmod.blocks.RunwaySignBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AirportSignsMod.MODID);

    public static final RegistryObject<Block> RUNWAY_SIGN = BLOCKS.register("runway_sign", () -> new RunwaySignBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion().mapColor(MapColor.COLOR_BLACK)));
    public static final RegistryObject<Block> ILS_SIGN = BLOCKS.register("ils_sign", () -> new ILSCriticalSignBlock(BlockBehaviour.Properties.of().strength(1.0F).noOcclusion()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
