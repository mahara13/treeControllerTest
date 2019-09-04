package com.vm.test.task.demo.ui.model;

import com.vm.test.task.demo.ui.renderer.UIRenderer;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ModelRendererPair {
    private UIRenderer renderer;
    private InternalTreeNode internalTreeNode;
}
