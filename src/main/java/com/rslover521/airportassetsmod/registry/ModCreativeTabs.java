package com.rslover521.airportassetsmod.registry;

import com.rslover521.airportassetsmod.AirportAssetsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AirportAssetsMod.MODID);

    public static final RegistryObject<CreativeModeTab> AIRPORT_TAB =
            CREATIVE_MODE_TABS.register("airport_tab", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.airportassetsmod.airport_tab"))
                            .icon(() -> new ItemStack(ModItems.RUNWAY_SIGN_ITEM.get()))
                            .displayItems((parameters, output) -> {
                                output.accept(ModItems.RUNWAY_SIGN_ITEM.get());
                            })
                            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
