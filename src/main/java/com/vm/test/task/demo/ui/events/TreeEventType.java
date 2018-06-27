package com.vm.test.task.demo.ui.events;

public enum TreeEventType {
    UNSUPPORTED_JSON_TYPE("UNSUPPORTED_JSON_TYPE"),
    VERTICAL_SCROLL("verticalScroll"),
    HORIZONTAL_SCROLL("horizontalScroll"),
    MOUSE_CLICK("mouseClick"),
    ITEM_CLICKED("itemClicked"),
    ITEM_EXPANDED("itemExpanded"),
    ITEM_COLLAPSED("itemCollapsed"),
    SELECTION_CHANGED("selectionChanged");

    private String type;

    TreeEventType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
