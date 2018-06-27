package com.vm.test.task.demo;

import com.vm.test.task.demo.ui.controller.TreeController;
import com.vm.test.task.demo.ui.events.TreeEvent;
import com.vm.test.task.demo.ui.events.TreeEventType;
import com.vm.test.task.demo.ui.model.InternalTreeNode;
import com.vm.test.task.demo.ui.renderer.TextRenderer;
import com.vm.test.task.demo.ui.renderer.UIRenderer;
import com.vm.test.task.demo.uiplatform.testplatform.UISystem;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeControlerApplicationTests {


    public TreeController treeController;
    @Before
    public void init() {
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println("     new test");
        System.out.println();

        treeController = new TreeController();
        URL jsonURL = ClassLoader.getSystemClassLoader().getResource("tree.json");
        try {
            treeController.setRendererClass(TextRenderer.class);
            treeController.setRectangle(new Rectangle(0,0, 100, 35));
            treeController.consume(new File(jsonURL.toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @After
    public void clearUISystem() {
        UISystem.getEventManager().clear();
    }

    @Test
    public void JSON_consumed_correctly() {
        InternalTreeNode lastVisibleChild = treeController.getLastVisibleChild();
        Assert.assertNotNull(lastVisibleChild);
        Assert.assertEquals(lastVisibleChild.getData(), "Parent1 1");
    }

    @Test
    public void scroll_down_and_check_if_last_view_element_is_correct() {
        UISystem.getEventManager().dispatchEvent(new TreeEvent(TreeEventType.VERTICAL_SCROLL, 15));
        InternalTreeNode lastVisibleChild = treeController.getLastVisibleChild();
        Assert.assertNotNull(lastVisibleChild);
        Assert.assertEquals(lastVisibleChild.getData(), "Child 3");
    }

    @Test
    public void expand_and_collapse_item_and_check_correctness_of_events_and_shown_events() {
        List<Object> list = new LinkedList<>();
        UISystem.getEventManager().addEventListener(TreeEventType.SELECTION_CHANGED.toString(), (e) -> list.add(e.getType()));
        UISystem.getEventManager().addEventListener(TreeEventType.ITEM_CLICKED.toString(), (e) -> list.add(e.getType()));
        UISystem.getEventManager().addEventListener(TreeEventType.ITEM_EXPANDED.toString(), (e) -> list.add(e.getType()));
        UISystem.getEventManager().addEventListener(TreeEventType.ITEM_COLLAPSED.toString(), (e) -> list.add(e.getType()));

        UISystem.getEventManager().dispatchEvent(new TreeEvent(TreeEventType.MOUSE_CLICK, new Point(2, 32)));
        UISystem.getEventManager().dispatchEvent(new TreeEvent(TreeEventType.VERTICAL_SCROLL, 5));

        InternalTreeNode lastVisibleChild = treeController.getLastVisibleChild();
        Assert.assertNotNull(lastVisibleChild);
        Assert.assertEquals(lastVisibleChild.getData(), "Child 1.1");

        UISystem.getEventManager().dispatchEvent(new TreeEvent(TreeEventType.MOUSE_CLICK, new Point(2, 17)));

        lastVisibleChild = treeController.getLastVisibleChild();
        Assert.assertNotNull(lastVisibleChild);
        Assert.assertEquals(lastVisibleChild.getData(), "Child 3");
        Assert.assertEquals(list.toString(),  "[itemClicked, itemExpanded, selectionChanged, itemClicked, itemCollapsed]");
    }
}
