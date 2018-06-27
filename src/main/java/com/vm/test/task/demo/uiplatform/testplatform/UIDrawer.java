package com.vm.test.task.demo.uiplatform.testplatform;

import com.vm.test.task.demo.uiplatform.Drawer;

import java.awt.*;

public class UIDrawer implements Drawer {
    @Override
    public void drawRect(Rectangle rectangle, String color) {
        System.out.println("Draw [" + color + "]  rect on " + rectangle.toString() + " position");
    }

    @Override
    public void cleanCanvas() {
        System.out.println("Clean canvas");
    }

    @Override
    public void drawTextWithSelectionMark(String text, boolean selected, Rectangle rectangle) {
        System.out.println("Draw text [" + text + "]   selected <" + selected + "> on " + rectangle);
    }
}
