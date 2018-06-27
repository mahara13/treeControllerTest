package com.vm.test.task.demo.ui.events;

import com.vm.test.task.demo.uiplatform.Event;

public class TreeEvent implements Event {
    private String type;
    private Object data;

    public TreeEvent(TreeEventType eventType) {
        this.type = eventType.toString();
        this.data = null;
    }

    public TreeEvent(TreeEventType eventType, Object data) {
        this.type = eventType.toString();
        this.data = data;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getData() {
        return data;
    }
}
