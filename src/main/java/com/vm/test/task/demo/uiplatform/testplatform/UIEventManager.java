package com.vm.test.task.demo.uiplatform.testplatform;

import com.vm.test.task.demo.uiplatform.Event;
import com.vm.test.task.demo.uiplatform.EventManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class UIEventManager implements EventManager {

    Map<String, List<Consumer<Event>>> map = new HashMap<>();

    @Override
    public void dispatchEvent(Event event) {
        System.out.println(event.getType());
        List<Consumer<Event>> list = map.get(event.getType());
        if ( list != null) {
            for (Consumer<Event> consumer: list) {
                consumer.accept(event);
            }
        }
    }

    @Override
    public void addEventListener(String eventType, Consumer<Event> eventHandler) {
        //For simplifying I didn't use some complex key of object that want to add event listener to itself
        if (map.get(eventType) == null) {
            map.put(eventType, new LinkedList<Consumer<Event>>());
        }

        map.get(eventType).add(eventHandler);
    }

    @Override
    public void clear() {
        map = new HashMap<>();
    }
}
