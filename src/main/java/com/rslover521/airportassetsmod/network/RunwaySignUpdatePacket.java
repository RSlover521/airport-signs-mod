package com.rslover521.airportassetsmod.network;

import com.rslover521.airportassetsmod.blockentity.RunwaySignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RunwaySignUpdatePacket {
    private final BlockPos pos;
    private final String text;

    public RunwaySignUpdatePacket(BlockPos pos, String text) {
        this.pos = pos;
        this.text = text;
    }

    public static void encode(RunwaySignUpdatePacket pkt, FriendlyByteBuf buf) {
        buf.writeBlockPos(pkt.pos);
        buf.writeUtf(pkt.text);
    }

    public static RunwaySignUpdatePacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        String text = buf.readUtf(32767);
        return new RunwaySignUpdatePacket(pos, text);
    }

    public static void handle(RunwaySignUpdatePacket msg, Supplier<NetworkEvent.Context> sup) {
        NetworkEvent.Context ctx = sup.get();

        ctx.enqueueWork(() -> {
            if (ctx.getSender() != null) {
                var player = ctx.getSender();
                var level = player.level();
                var be = level.getBlockEntity(msg.pos);
                if (be instanceof RunwaySignBlockEntity sign) {
                    sign.setRunway(msg.text);
                }
            } else {
                var level = Minecraft.getInstance().level;
                if (level == null) return;
                var be = level.getBlockEntity(msg.pos);
                if (be instanceof RunwaySignBlockEntity sign) {
                    sign.setRunway(msg.text);
                }
            }
        });

        ctx.setPacketHandled(true);
    }
}
