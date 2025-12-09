package com.rslover521.airportassetsmod.registry;

import com.rslover521.airportassetsmod.AirportAssetsMod;
import com.rslover521.airportassetsmod.blockentity.RunwaySignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AirportAssetsMod.MODID);

    public static final RegistryObject<BlockEntityType<RunwaySignBlockEntity>> RUNWAY_SIGN =
            BLOCK_ENTITIES.register("runway_sign", () ->
                    BlockEntityType.Builder.of(RunwaySignBlockEntity::new, ModBlocks.RUNWAY_SIGN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
