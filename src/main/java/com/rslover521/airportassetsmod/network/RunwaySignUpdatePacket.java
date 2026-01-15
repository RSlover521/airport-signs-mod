package com.rslover521.airportassetsmod.network;

import com.rslover521.airportassetsmod.blockentity.RunwaySignBlockEntity;
import com.rslover521.airportassetsmod.menu.RunwaySignMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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

    public static void handle(RunwaySignUpdatePacket pkt, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) return;

            if (player.containerMenu instanceof RunwaySignMenu menu) {
                menu.setRunway(player, pkt.text);
            }
        });
        ctx.setPacketHandled(true);
    }
}
