package com.vm.test.task.demo.ui.model;

import java.util.ArrayList;
import java.util.List;

public class ITreeNode implements InternalTreeNode {

    public static final int ROOT_LEVEL = -1;

    private int level;
    boolean collapsed = true;
    boolean selected = false;
    private Object data;
    private List<InternalTreeNode> children;
    private InternalTreeNode parentNode;


    public ITreeNode() {
        children = new ArrayList<>();
        setLevel(ROOT_LEVEL);
    }

    public ITreeNode(Object data, InternalTreeNode parenInternalTreeNode) {
        children = new ArrayList<>();
        setData(data);
        setParentTreeNode(parenInternalTreeNode);
        setLevel(parenInternalTreeNode.getLevel() + 1);
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public boolean getCollapsed() {
        return collapsed;
    }

    @Override
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    @Override
    public void setParentTreeNode(InternalTreeNode treeNode) {
        parentNode = treeNode;
    }

    @Override
    public InternalTreeNode getParenTreeNode() {
        return parentNode;
    }

    @Override
    public boolean hasChildren() {
        return children != null && children.size() > 0;
    }

    @Override
    public void setSelected(boolean b) {
        selected = b;
    }

    @Override
    public boolean getSelected() {
        return selected;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public List<InternalTreeNode> getChildren() {
        return children;
    }
}
