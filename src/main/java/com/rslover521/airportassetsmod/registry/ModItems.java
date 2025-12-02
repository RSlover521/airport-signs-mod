package com.rslover521.airportassetsmod.registry;

import com.rslover521.airportassetsmod.AirportAssetsMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AirportAssetsMod.MODID);

    public static final RegistryObject<Item> RUNWAY_SIGN_ITEM = ITEMS.register("runway_sign", () -> new BlockItem(ModBlocks.RUNWAY_SIGN.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
