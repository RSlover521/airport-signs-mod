package com.rslover521.airportsignsmod.network;

import com.rslover521.airportsignsmod.AirportSignsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(AirportSignsMod.MODID, "main"))
            .clientAcceptedVersions(PROTOCOL::equals)
            .serverAcceptedVersions(PROTOCOL::equals)
            .networkProtocolVersion(() -> PROTOCOL)
            .simpleChannel();

    private static int id = 0;
    public static int id() { return id++; }

    public static void registerMessages() {
        CHANNEL.registerMessage(id(), RunwaySignUpdatePacket.class,
                RunwaySignUpdatePacket::encode,
                RunwaySignUpdatePacket::decode,
                RunwaySignUpdatePacket::handle);
    }
}
