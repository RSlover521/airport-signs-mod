package com.rslover521.airportsignsmod.network;

import com.rslover521.airportsignsmod.blockentity.RunwaySignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
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
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            var be = player.level().getBlockEntity(msg.pos);
            if (be instanceof RunwaySignBlockEntity sign) {
                sign.setRunway(msg.text);
            }
        });

        ctx.setPacketHandled(true);
    }
}
