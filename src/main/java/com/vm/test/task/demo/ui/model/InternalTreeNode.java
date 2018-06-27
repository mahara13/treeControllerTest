package com.vm.test.task.demo.ui.model;

import java.util.List;

public interface InternalTreeNode {
    Object getData();
    List<InternalTreeNode> getChildren();

    void setLevel(int level);
    int getLevel();
    void setCollapsed(boolean collapsed);
    boolean getCollapsed();
    void setParentTreeNode(InternalTreeNode treeNode);
    InternalTreeNode getParenTreeNode();

    boolean hasChildren();

    void setSelected(boolean b);
    boolean getSelected();
}
