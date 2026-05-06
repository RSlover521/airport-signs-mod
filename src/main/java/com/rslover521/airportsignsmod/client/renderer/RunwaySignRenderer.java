package com.rslover521.airportsignsmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.rslover521.airportsignsmod.blockentity.RunwaySignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class RunwaySignRenderer implements BlockEntityRenderer<RunwaySignBlockEntity> {
    public RunwaySignRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(RunwaySignBlockEntity sign, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        String text = sign.getRunway();
        if (text == null || text.isEmpty()) return;

        poseStack.pushPose();

        // position: center of block, Y around 10/16f (matches model)
        poseStack.translate(0.5, 10f/16f, 0.5);

        BlockState state = sign.getBlockState();
        Direction dir = state.getValue(HorizontalDirectionalBlock.FACING);

        float yRot;
        switch (dir) {
            case NORTH -> yRot = 180f;
            case WEST  -> yRot = 90f;
            case EAST  -> yRot = 270f;
            default    -> yRot = 0f;
        }
        poseStack.mulPose(com.mojang.math.Axis.YP.rotationDegrees(yRot));

        // nudge forward so text doesn't Z-fight with model face
        poseStack.translate(0, 0, -0.502);

        // scale for readable size
        float scale = 0.025f * 1.8f;
        poseStack.scale(scale, -scale, scale);

        Font font = Minecraft.getInstance().font;
        float width = font.width(text);

        // drawInBatch wants a Component (not a raw String) and DisplayMode enum
        font.drawInBatch(
                Component.literal(text),
                -width / 2f,
                0f,
                0xFFFFFFFF,
                false,
                poseStack.last().pose(),
                bufferSource,
                Font.DisplayMode.NORMAL,
                0,
                packedLight
        );

        poseStack.popPose();
    }

}
