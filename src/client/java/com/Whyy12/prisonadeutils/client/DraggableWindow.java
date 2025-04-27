package com.Whyy12.prisonadeutils.client;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;

public class DraggableWindow {
    public int x, y, width, height;
    private int dragOffsetX, dragOffsetY;
    private boolean dragging = false;

    private final List<AttachedElement> attachedElements = new ArrayList<>();
    private final List<AttachedText> attachedTexts = new ArrayList<>();

    public DraggableWindow(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Checks if the mouse is inside the window
    private boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void onMousePressed(double mouseX, double mouseY, int button) {
        if (button == 0 && isMouseOver(mouseX, mouseY)) {
            dragging = true;
            dragOffsetX = (int) (mouseX - x);
            dragOffsetY = (int) (mouseY - y);
        }
    }

    public void onMouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) {
            dragging = false;
        }
    }

    public void onMouseDragged(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragOffsetX);
            y = (int) (mouseY - dragOffsetY);

            CalculatorState.windowX = x;
            CalculatorState.windowY = y;
        }
    }

    public void attachElement(Element element, int offsetX, int offsetY) {
        attachedElements.add(new AttachedElement(element, offsetX, offsetY));
        updateAttachedElementPosition(element, offsetX, offsetY);
    }

    public void updateAttachedPositions() {
        for (AttachedElement attached : attachedElements) {
            updateAttachedElementPosition(attached.element, attached.offsetX, attached.offsetY);
        }
    }

    private void updateAttachedElementPosition(Element element, int offsetX, int offsetY) {
        if (element instanceof com.Whyy12.prisonadeutils.client.Button customButton) {
            customButton.setX(x + offsetX);
            customButton.setY(y + offsetY);
        } else if (element instanceof TextFieldWidget entryBox) {
            entryBox.setX(x + offsetX);
            entryBox.setY(y + offsetY);
        }
    }

    public void attachText(Text text, int offsetX, int offsetY) {
        attachedTexts.add(new AttachedText(text, offsetX, offsetY));
    }

    private static class AttachedElement {
        public final Element element;
        public final int offsetX, offsetY;
        public AttachedElement(Element element, int offsetX, int offsetY) {
            this.element = element;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
    }

    private static class AttachedText {
        int offsetX, offsetY;
        Text text;

        AttachedText(Text text, int offsetX, int offsetY) {
            this.text = text;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
        }
    }
}