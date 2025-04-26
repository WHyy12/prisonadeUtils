package com.Whyy12.prisonadeutils.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class PrisonadeutilsClient implements ClientModInitializer {
    // Divides with remainders and returns both the quotient and remainder
    public static int[] divmod(int dividend, int divisor) {
        int quotient = dividend / divisor;
        int remainder = dividend % divisor;

        return new int[] {quotient, remainder};
    }

    private static final Identifier CALCULATOR_LAYER_ID = Identifier.of("prisonadeutils", "calculator_display");

    public static KeyBinding openCalculatorKey;

    @Override
    public void onInitializeClient() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(IdentifiedLayer.CHAT, CALCULATOR_LAYER_ID, PrisonadeutilsClient::render));

        // Register open calculator keybind
        openCalculatorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("Open Calculator",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "Prisonadeutils"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openCalculatorKey.wasPressed()) {
                client.setScreen(new CalculatorScreen());
            }
        });
    }

    MinecraftClient client = MinecraftClient.getInstance();
    TextRenderer textRenderer = client.textRenderer;

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        final MinecraftClient client = MinecraftClient.getInstance();
        final TextRenderer textRenderer = client.textRenderer;

        int y = 0;

        context.drawTextWithShadow(textRenderer, CalculatorState.selectedArea + " - " + CalculatorState.selectedMix, 0, y, 0xFFFFFF);

        y += textRenderer.fontHeight;
        for (int i = 0; i < CalculatorState.ores.size(); i++) {
            int[] stacks = divmod(CalculatorState.amounts.get(i) * CalculatorState.mixAmount, 64);
            int[] pvs = divmod(stacks[0], 54);

            // Render amounts of needed ore in amount of stacks and amount of pvs filled.
            context.drawTextWithShadow(textRenderer, CalculatorState.amounts.get(i) + ": " + pvs[0] + " Pvs, " + pvs[1] + " stacks, and " + stacks[1], 0, y, 0xFFFFFF);
            y += textRenderer.fontHeight;
        }
    }
}