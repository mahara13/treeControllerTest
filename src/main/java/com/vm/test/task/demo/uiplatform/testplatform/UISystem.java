package com.vm.test.task.demo.uiplatform.testplatform;

import com.vm.test.task.demo.uiplatform.Drawer;
import com.vm.test.task.demo.uiplatform.EventManager;

public class UISystem {

    public static EventManager dispatcher;

    public static EventManager getEventManager() {
        if(dispatcher == null) {
            dispatcher = new UIEventManager();
        }
        return dispatcher;
    }

    public static Drawer getDrawer() {
        return new UIDrawer();
    }
}
