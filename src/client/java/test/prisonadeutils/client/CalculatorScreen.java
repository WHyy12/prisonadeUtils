package test.prisonadeutils.client;

import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CalculatorScreen extends Screen {
    private TextFieldWidget mixAmountBox;
    private static final Identifier TEXTURE = Identifier.of("prisonadeutils", "/textures/gui/calculator.png");

    protected CalculatorScreen() {
        super(Text.of("Calculator"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, 0, 0, 0, 0, 130, 200, 16, 16);

        int y = 4;

        // Area selection
        for (String areaName : CalculatorState.AREAS.keySet()) {
            context.drawTextWithShadow(this.textRenderer, areaName, 16, y, areaName.equals(CalculatorState.selectedArea) ? 0xFFA500 : 0xFFFFFF);
            y += 16;
        }

        // Mix selection
        y = 100;
        Map<String, Map<String, Integer>> mixes = CalculatorState.AREAS.get(CalculatorState.selectedArea);
        for (Map.Entry<String, Map<String, Integer>> mixEntry : mixes.entrySet()) {
            String mixName = mixEntry.getKey();
            context.drawTextWithShadow(this.textRenderer, mixName, 16, y, mixName.equals(CalculatorState.selectedMix) ? 0xFFA500 : 0xFFFFFF);
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
        int y = 0;

        // Buttons
        for (String areaName : CalculatorState.AREAS.keySet()) {
            if (CalculatorState.AREAS.containsKey(areaName)) {
                this.addDrawableChild(
                        new Button(0, y, 16, 16, Text.of(""), btn -> {
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
                        })
                );
            }
            y += 16;
        }

        y = 96;
        Map<String, Map<String, Integer>> mixes = CalculatorState.AREAS.get(CalculatorState.selectedArea);
        for (Map.Entry<String, Map<String, Integer>> mixEntry : mixes.entrySet()) {
            String mixName = mixEntry.getKey();
            Map<String, Integer> oreReq = mixEntry.getValue();

            this.addDrawableChild(
                    new Button(0, y, 16, 16, Text.of(mixName), btn -> {
                        CalculatorState.selectedMix = mixName;

                        CalculatorState.ores.clear();
                        CalculatorState.amounts.clear();

                        for (Map.Entry<String, Integer> ore : oreReq.entrySet()) {
                            CalculatorState.ores.add(ore.getKey());
                            CalculatorState.amounts.add(ore.getValue());
                        }
                    })
            );
            y += 16;
        }

        this.mixAmountBox = new TextFieldWidget(
                this.textRenderer, 0, 170, 50, 20,
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
    }
    @Override
    public boolean shouldPause() {
        return false;
    }
}