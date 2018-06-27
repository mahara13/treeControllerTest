package com.vm.test.task.demo.ui.dataproviders;

import com.vm.test.task.demo.ui.model.InternalTreeNode;
import com.vm.test.task.demo.uiplatform.EventManager;

public interface TreeStructureProvider {
    InternalTreeNode parseData(Object source, EventManager dispatcher);
}
