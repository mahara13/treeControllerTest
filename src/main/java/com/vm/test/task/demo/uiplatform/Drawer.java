package com.vm.test.task.demo.uiplatform;

import java.awt.*;

public interface Drawer {
    void drawRect(Rectangle rectangle, String color);

    void cleanCanvas();

    void drawTextWithSelectionMark(String text, boolean selected, Rectangle rectangle);
}
