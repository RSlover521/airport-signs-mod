package com.rslover521.airportassetsmod.client.screen;

import com.rslover521.airportassetsmod.menu.RunwaySignMenu;
import com.rslover521.airportassetsmod.network.NetworkHandler;
import com.rslover521.airportassetsmod.network.RunwaySignUpdatePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.regex.Pattern;

public class RunwaySignScreen extends Screen implements MenuAccess<RunwaySignMenu> {

    private static final Pattern VALID = Pattern.compile("^(0[1-9]|[1-3][0-9])(L|R|C)?$");

    private final RunwaySignMenu menu;
    private EditBox input;

    public RunwaySignScreen(RunwaySignMenu menu, Inventory inv, Component title) {
        super(title);
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Input text box
        input = new EditBox(this.font, centerX - 70, centerY - 20, 140, 18, Component.literal("Runway"));
        input.setMaxLength(3);
        input.setFocused(true);
        this.addRenderableWidget(input);

        // DONE button
        this.addRenderableWidget(
                Button.builder(Component.literal("Done"), b -> {
                    String text = input.getValue().toUpperCase().trim();

                    if (VALID.matcher(text).matches()) {
                        NetworkHandler.CHANNEL.sendToServer(
                                new RunwaySignUpdatePacket(menu.getBlockEntity().getBlockPos(), text)
                        );
                        onClose();
                    } else {
                        input.setValue("");
                    }
                }).bounds(centerX + 10, centerY + 10, 80, 20).build()
        );

        // CANCEL button
        this.addRenderableWidget(
                Button.builder(Component.literal("Cancel"), b -> onClose())
                        .bounds(centerX - 90, centerY + 10, 80, 20)
                        .build()
        );
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        // Darken background
        this.renderBackground(gfx);

        // Draw title BEFORE widgets
        gfx.drawCenteredString(this.font, Component.literal("Runway Sign Editor"),
                this.width / 2, this.height / 2 - 70, 0xFFFFFF);

        // Draw widgets
        super.render(gfx, mouseX, mouseY, partialTicks);

        // ---- PREVIEW AREA ----
        int px = this.width / 2 - 80;
        int py = this.height / 2 - 45;

        // Sign preview rectangle (dark red)
        gfx.fill(px, py, px + 160, py + 30, 0xCC880000); // 0xCC = alpha

        // Text preview
        String text = input.getValue().toUpperCase();
        if (text.isEmpty()) text = "";

        gfx.pose().pushPose();
        gfx.pose().translate(this.width / 2, py + 15, 0);
        gfx.pose().scale(1.8f, 1.8f, 1.8f);

        int tw = this.font.width(text);
        gfx.drawString(this.font, text, -tw / 2, -4, 0xFFFFFFFF, false);

        gfx.pose().popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public RunwaySignMenu getMenu() {
        return this.menu;
    }
}
