package com.Whyy12.prisonadeutils.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculatorScreen extends Screen {
    private int windowX = 0;
    private int windowY = 0;
    private TextFieldWidget mixAmountBox;
    private DraggableWindow window;

    protected CalculatorScreen() {
        super(Text.of(""));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        window.onMousePressed(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        window.onMouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        window.onMouseDragged(mouseX, mouseY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        window.updateAttachedPositions();

        context.fill(window.x, window.y, window.x + window.width, window.y + window.height, 0xAA000000);

        int y = 4;

        // Area selection
        for (String areaName : CalculatorState.AREAS.keySet()) {
            context.drawTextWithShadow(this.textRenderer, areaName, 16 + CalculatorState.windowX, y + CalculatorState.windowY, areaName.equals(CalculatorState.selectedArea) ? 0xFFA500 : 0xFFFFFF);
            y += 16;
        }

        // Mix selection
        y = 100;
        Map<String, Map<String, Integer>> mixes = CalculatorState.AREAS.get(CalculatorState.selectedArea);
        for (Map.Entry<String, Map<String, Integer>> mixEntry : mixes.entrySet()) {
            String mixName = mixEntry.getKey();
            context.drawTextWithShadow(this.textRenderer, mixName, 16 + CalculatorState.windowX, y + CalculatorState.windowY, mixName.equals(CalculatorState.selectedMix) ? 0xFFA500 : 0xFFFFFF);
            y += 16;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // Empty to prvent screen blur
        // May add an option to turn blur on
    }

    @Override
    protected void init() {
        window = new DraggableWindow(CalculatorState.windowX, CalculatorState.windowY, 200, 170);

        int y = 0;

        // Buttons
        for (String areaName : CalculatorState.AREAS.keySet()) {
            if (CalculatorState.AREAS.containsKey(areaName)) {
                Button areaButton = new Button(windowX, y, 16, 16, Text.of(""), btn -> {
                    Map<String, Map<String, Integer>> oldMixes = CalculatorState.AREAS.get(CalculatorState.selectedArea);
                    List<String> oldMixKeys = new ArrayList<>(oldMixes.keySet());
                    int index = oldMixKeys.indexOf(CalculatorState.selectedMix);

                    CalculatorState.selectedArea = areaName;

                    Map<String, Map<String, Integer>> newMixes = CalculatorState.AREAS.get(areaName);
                    List<String> newMixKeys = new ArrayList<>(newMixes.keySet());

                    if (index >= 0 && index < newMixKeys.size()) {
                        CalculatorState.selectedMix = newMixKeys.get(index);
                    } else {
                        CalculatorState.selectedMix = newMixKeys.getFirst();
                    }

                    CalculatorState.selectedMix = newMixKeys.get(index);

                    CalculatorState.ores.clear();
                    CalculatorState.amounts.clear();

                    Map<String, Integer> oreReq = newMixes.get(CalculatorState.selectedMix);
                    for (Map.Entry<String, Integer> ore : oreReq.entrySet()) {
                        CalculatorState.ores.add(ore.getKey());
                        CalculatorState.amounts.add(ore.getValue());
                    }

                    assert this.client != null;
                    this.init(this.client, this.width, this.height);
                });

                this.addDrawableChild(areaButton);

                window.attachElement(areaButton, 0, y);
            }
            y += 16;
        }

        y = 96;
        Map<String, Map<String, Integer>> mixes = CalculatorState.AREAS.get(CalculatorState.selectedArea);
        for (Map.Entry<String, Map<String, Integer>> mixEntry : mixes.entrySet()) {
            String mixName = mixEntry.getKey();
            Map<String, Integer> oreReq = mixEntry.getValue();

            Button mixButton = new Button(windowX, y + windowY, 16, 16, Text.of(mixName), btn -> {
                CalculatorState.selectedMix = mixName;

                CalculatorState.ores.clear();
                CalculatorState.amounts.clear();

                for (Map.Entry<String, Integer> ore : oreReq.entrySet()) {
                    CalculatorState.ores.add(ore.getKey());
                    CalculatorState.amounts.add(ore.getValue());
                }
            });

            this.addDrawableChild(mixButton);
            window.attachElement(mixButton, 0, y);

            y += 16;
        }

        this.mixAmountBox = new TextFieldWidget(
                this.textRenderer, windowX, 170 + windowY, 50, 20,
                Text.of("")
        );
        this.mixAmountBox.setMaxLength(5);
        this.mixAmountBox.setText(String.valueOf(CalculatorState.mixAmount));
        this.mixAmountBox.setChangedListener(text -> {
            try {
                int val = Integer.parseInt(text);
                CalculatorState.mixAmount = Math.max(1, val); // minimum of 1
            } catch (NumberFormatException ignored) {
                // Prevents incorrect input from crashing game
            }
        });
        this.addDrawableChild(mixAmountBox);
        window.attachElement(mixAmountBox, 0, 170);
    }
    @Override
    public boolean shouldPause() {
        return false;
    }
}