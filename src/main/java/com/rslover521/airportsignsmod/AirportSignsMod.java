package com.rslover521.airportsignsmod;

import com.mojang.logging.LogUtils;
import com.rslover521.airportsignsmod.client.renderer.RunwaySignRenderer;
import com.rslover521.airportsignsmod.client.screen.RunwaySignScreen;
import com.rslover521.airportsignsmod.network.NetworkHandler;
import com.rslover521.airportsignsmod.registry.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AirportSignsMod.MODID)
public class AirportSignsMod {
    public static final String MODID = "airportsignsmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AirportSignsMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenus.register(modEventBus);
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
