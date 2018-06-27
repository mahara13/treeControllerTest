package com.vm.test.task.demo.ui.model;

import com.vm.test.task.demo.ui.renderer.UIRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelRendererPair {
    private UIRenderer renderer;
    private InternalTreeNode internalTreeNode;
}
