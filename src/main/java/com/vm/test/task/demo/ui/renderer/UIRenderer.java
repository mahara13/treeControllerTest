package com.vm.test.task.demo.ui.renderer;

import java.awt.*;

public interface UIRenderer {
    void setData(Object data);
    void setSelected(boolean value);
    void setRectangle(Rectangle rectangle);
    Rectangle getRectangle();
    void draw();
}
