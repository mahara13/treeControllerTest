package com.vm.test.task.demo.uiplatform;

import java.util.function.Consumer;

public interface EventManager {
    void dispatchEvent(Event event);
    void addEventListener(String eventType, Consumer<Event> eventHandler);

    void clear();
}
