package com.rslover521.airportassetsmod.registry;

import com.rslover521.airportassetsmod.AirportAssetsMod;
import com.rslover521.airportassetsmod.menu.RunwaySignMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, AirportAssetsMod.MODID);

    public static final RegistryObject<MenuType<RunwaySignMenu>> RUNWAY_SIGN =
            MENUS.register("runway_sign",
                () -> IForgeMenuType.create(RunwaySignMenu::fromNetwork)
            );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
