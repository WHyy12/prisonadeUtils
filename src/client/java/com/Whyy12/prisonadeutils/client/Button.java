package com.Whyy12.prisonadeutils.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
public class Button extends PressableWidget {
    public interface PressAction {
        void onPress(Button button);
    }

    private static final Identifier ButtonTex = Identifier.of("prisonadeutils", "textures/gui/button.png");
    private final PressAction onPress;

    public Button(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
            int vOffset = isHovered() ? 16 : 0;
            context.drawTexture(RenderLayer::getGuiTextured, ButtonTex, getX(), getY(), 0, vOffset, width, height, 16, 32);
        }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }
}