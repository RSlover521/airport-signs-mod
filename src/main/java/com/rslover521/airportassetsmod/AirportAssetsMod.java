package com.rslover521.airportassetsmod;

import com.mojang.logging.LogUtils;
import com.rslover521.airportassetsmod.client.renderer.RunwaySignRenderer;
import com.rslover521.airportassetsmod.client.screen.RunwaySignScreen;
import com.rslover521.airportassetsmod.network.NetworkHandler;
import com.rslover521.airportassetsmod.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AirportAssetsMod.MODID)
public class AirportAssetsMod {
    public static final String MODID = "airportassetsmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AirportAssetsMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        ModCreativeTabs.register(modEventBus);

        NetworkHandler.registerMessages();

        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::commonSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        BlockEntityRenderers.register(ModBlockEntities.RUNWAY_SIGN.get(), RunwaySignRenderer::new);
        MenuScreens.register(ModMenus.RUNWAY_SIGN.get(), RunwaySignScreen::new);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Airport Assets Mod Loaded");
        LOGGER.info("Hello Forge World");
    }
}
