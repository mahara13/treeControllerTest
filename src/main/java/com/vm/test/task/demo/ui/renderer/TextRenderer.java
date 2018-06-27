package com.vm.test.task.demo.ui.renderer;

import com.vm.test.task.demo.uiplatform.Drawer;
import com.vm.test.task.demo.uiplatform.testplatform.UISystem;

import java.awt.*;

public class TextRenderer implements UIRenderer {

    public TextRenderer() {
        drawer = UISystem.getDrawer();
    }

    private String text;
    @Override
    public void setData(Object data) {
        this.text = String.valueOf(data);
    }

    private boolean selected;
    @Override
    public void setSelected(boolean value) {
        selected = value;
    }

    private Rectangle rectangle;
    @Override
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    private Drawer drawer;
    @Override
    public void draw() {
        drawer.drawTextWithSelectionMark(text, selected, rectangle);
    }
}
