package com.vm.test.task.demo.ui.controller;

import com.vm.test.task.demo.ui.dataproviders.JSONStructureProvider;
import com.vm.test.task.demo.ui.events.TreeEvent;
import com.vm.test.task.demo.ui.events.TreeEventType;
import com.vm.test.task.demo.ui.model.InternalTreeNode;
import com.vm.test.task.demo.ui.model.ModelRendererPair;
import com.vm.test.task.demo.ui.renderer.UIRenderer;
import com.vm.test.task.demo.uiplatform.Drawer;
import com.vm.test.task.demo.uiplatform.Event;
import com.vm.test.task.demo.uiplatform.EventManager;
import com.vm.test.task.demo.uiplatform.testplatform.UISystem;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class TreeController {

    private int scrollTrackThickness = 5;
    private final EventManager eventManager;
    private List<InternalTreeNode> listToShow = new LinkedList<>();
    private List<ModelRendererPair> currentlyShowingItems = new LinkedList<>();
    private InternalTreeNode currentlySelectedNode = null;
    private ScrollController verticalScrollController = new ScrollController();
    private ScrollController horizontalScrollController = new ScrollController();

    private Class<? extends UIRenderer> rendererClass;
    private Drawer drawer;
    public void setRendererClass(Class<? extends UIRenderer> textRendererClass) {
        rendererClass = textRendererClass;
        //TODO add logic to update view
    }

    private Rectangle boundaries;
    public void setRectangle( Rectangle rectangle) {
        if ((rectangle != null) &&
                (rectangle.equals(boundaries) == false)) {
            boundaries = rectangle;
            updateView();
        }
    }

    private InternalTreeNode root;
    private void setRoot(InternalTreeNode rootCandidate) {
        if (rootCandidate != null && rootCandidate.equals(root) == false) {
            root = rootCandidate;
            listToShow.clear();
            listToShow.addAll(root.getChildren());
            updateView();
        }
    }

    private int levelPadding = 10;
    public void setLevelPadding(int newLevelPadding) {
        if (newLevelPadding != levelPadding) {
            levelPadding = newLevelPadding;
            updateView();
        }
    }

    private Point basePoint = new Point(0,0);
    private void setBasePoint(Point newBasePoint) {
        if (newBasePoint.equals(basePoint) == false) {
            basePoint = newBasePoint;
            updateView();
        }
    }

    private int rendererHeight = 15;
    public void setRendererHeight(int newRendererHeight) {
        if (rendererHeight != newRendererHeight) {
            this.rendererHeight = newRendererHeight;
            updateView();
        }
    }

    private int rendererWidth = 80;
    public void setRendererWidth(int newRendererWidth) {
        if (rendererWidth != newRendererWidth) {
            this.rendererWidth = newRendererWidth;
            updateView();
        }
    }

    public TreeController() {
        eventManager = UISystem.getEventManager();
        drawer = UISystem.getDrawer();
        eventManager.addEventListener(TreeEventType.VERTICAL_SCROLL.toString(), e -> handleVerticalScroll(e));
        eventManager.addEventListener(TreeEventType.HORIZONTAL_SCROLL.toString(), e -> handleHorizontalScroll(e));
        eventManager.addEventListener(TreeEventType.MOUSE_CLICK.toString(), e -> handleMouseClick(e));
    }

    private void handleMouseClick(Event e) {
        Point point = (Point)e.getData();

        Rectangle horizontalTrackRectangle = getHorizontalTrackRectangle();
        Rectangle verticalTrackRectangle = getVerticalTrackRectangle();
        if (horizontalTrackRectangle != null && horizontalTrackRectangle.contains(point)) {
            //TODO some manipulation with scrolling will go here
        } else if (verticalTrackRectangle != null && verticalTrackRectangle.contains(point)) {
            //TODO some manipulation with scrolling will go here
        } else {
            checkIfItemIsClicked(point);
        }

    }

    private void checkIfItemIsClicked(Point point) {
        for (ModelRendererPair pair: currentlyShowingItems) {
            Rectangle rectangle = pair.getRenderer().getRectangle();
            if (rectangle != null && rectangle.contains(point)) {
                processItemClick(pair);
                break;
            }
        }
    }

    private void processItemClick(ModelRendererPair pair) {
        InternalTreeNode node = pair.getInternalTreeNode();
        if (node.hasChildren()) {
            if (node.getCollapsed() == true) {
                //Expanding
                listToShow.addAll(listToShow.indexOf(node) + 1, node.getChildren());
                node.setCollapsed(false);
                updateView();
                eventManager.dispatchEvent(new TreeEvent(TreeEventType.ITEM_CLICKED, node.getData()));
                eventManager.dispatchEvent(new TreeEvent(TreeEventType.ITEM_EXPANDED, node.getData()));
            } else {
                //Collapsing
                listToShow.removeAll(node.getChildren());
                node.setCollapsed(true);
                updateView();
                eventManager.dispatchEvent(new TreeEvent(TreeEventType.ITEM_CLICKED, node.getData()));
                eventManager.dispatchEvent(new TreeEvent(TreeEventType.ITEM_COLLAPSED, node.getData()));
            }
        } else {
            eventManager.dispatchEvent(new TreeEvent(TreeEventType.ITEM_CLICKED, node.getData()));
        }

        boolean isNotTheSameNode = currentlySelectedNode != node;
        if (isNotTheSameNode) {
            if (currentlySelectedNode != null) {
                currentlySelectedNode.setSelected(false);
            }
            currentlySelectedNode = node;
            currentlySelectedNode.setSelected(true);
            eventManager.dispatchEvent(new TreeEvent(TreeEventType.SELECTION_CHANGED, node.getData()));
        }
    }

    private void handleHorizontalScroll(Event e) {
        int shifting = (int)e.getData();
        if(horizontalScrollController.calculate(shifting)) {
            setBasePoint(new Point(horizontalScrollController.getBasePointShifting(), basePoint.y));
        }
    }

    private void handleVerticalScroll(Event e) {
        int shifting = (int)e.getData();
        if(verticalScrollController.calculate(shifting)) {
            setBasePoint(new Point(basePoint.x, verticalScrollController.getBasePointShifting()));
        }
    }

    private void updateView() {
        cleanCanvas();
        drawList();
    }

    private void drawList() {
        int i = 0;
        currentlyShowingItems.clear();
        Rectangle max = new Rectangle();
        for (InternalTreeNode node : listToShow) {
            Rectangle rectangle = getBoundaries(node, i);
            max.add(rectangle);
            if (willBeVisible(rectangle)) {
                UIRenderer renderer = getRenderer();
                renderer.setData(node.getData());
                renderer.setSelected(node.getSelected());
                renderer.setRectangle(rectangle);
                renderer.draw();
                currentlyShowingItems.add(new ModelRendererPair(renderer, node));
            }
            i++;
        }

        checkIfScrollBarsShouldBeAdded(max);
    }

    private void checkIfScrollBarsShouldBeAdded(Rectangle max) {
        verticalScrollController.calculate(boundaries.height, max.height);
        int freeHorizontalSpace = verticalScrollController.isVisible() ? boundaries.width - scrollTrackThickness : boundaries.width;
        horizontalScrollController.calculate(freeHorizontalSpace, max.width);
        if (horizontalScrollController.isVisible() == true) {
            verticalScrollController.calculate(boundaries.height - scrollTrackThickness, max.height);
        }

        updateVerticalScroll();
        updateHorizontalScroll();
        if (isBothScrollbarsShowing()) {
            drawScrollJoinCorner();
        }

        verticalScrollController.changesApplied();
        horizontalScrollController.changesApplied();
    }

    private boolean isBothScrollbarsShowing() {
        return horizontalScrollController.isVisible() && verticalScrollController.isVisible();
    }

    private Rectangle getScrollJoinCornerRectangle() {
        if (isBothScrollbarsShowing()) {
            return new Rectangle(boundaries.width - scrollTrackThickness, boundaries.height - scrollTrackThickness, scrollTrackThickness, scrollTrackThickness);
        }

        return null;
    }

    private void drawScrollJoinCorner() {
        drawRect(getScrollJoinCornerRectangle(),  "ScrollJoinCorner #111111");
    }

    private void updateHorizontalScroll() {
        //As we clean before draw, we just draw if it shown, no need to hide
        if( horizontalScrollController.isVisible() ) {
            drawHorizontalTrack();
            drawHorizontalTrackBar();
        }
    }

    private void drawHorizontalTrackBar() {
        drawRect(getHorizontalScrollBarRectangle(),  " HorizontalTrackBar #FFFF00");
    }

    private Rectangle getHorizontalScrollBarRectangle() {
        if (horizontalScrollController.isVisible()) {
            int xPos = horizontalScrollController.getScrollPosition();
            return new Rectangle(xPos, boundaries.height - scrollTrackThickness, horizontalScrollController.getScrollLength(), scrollTrackThickness);
        }

        return null;
    }

    private Rectangle getHorizontalTrackRectangle() {
        if (horizontalScrollController.isVisible()) {
            int trackWidth = isBothScrollbarsShowing() ? boundaries.width - scrollTrackThickness : boundaries.width;
            return new Rectangle(0, boundaries.height - scrollTrackThickness, trackWidth, scrollTrackThickness);
        }

        return null;
    }

    private void drawHorizontalTrack() {
        drawRect(getHorizontalTrackRectangle(),  " HorizontalTrack #FF0000");
    }

    private void drawRect(Rectangle rectangle, String color) {
        drawer.drawRect(rectangle, color);
    }

    private void updateVerticalScroll() {
        //As we clean before draw, we just draw if it shown, no need to hide
        if( verticalScrollController.isVisible() ) {
            drawVerticalTrack();
            drawVerticalTrackBar();
        }
    }

    private Rectangle getVerticalScrollBarRectangle() {
        if (verticalScrollController.isVisible()) {
            int yPos = verticalScrollController.getScrollPosition();
            return new Rectangle(boundaries.width - scrollTrackThickness, yPos, scrollTrackThickness, verticalScrollController.getScrollLength());
        }

        return null;
    }

    private void drawVerticalTrackBar() {
        drawRect(getVerticalScrollBarRectangle(),  "VerticalTrackBar #FFFF00");
    }

    private Rectangle getVerticalTrackRectangle() {
        if (verticalScrollController.isVisible()) {
            int trackHeight = isBothScrollbarsShowing() ? boundaries.height - scrollTrackThickness : boundaries.height;
            return new Rectangle(boundaries.width - scrollTrackThickness, 0, scrollTrackThickness, trackHeight);
        }

        return null;
    }

    private void drawVerticalTrack() {
        drawRect(getVerticalTrackRectangle(),  "VerticalTrack #FF0000");
    }

    private boolean willBeVisible(Rectangle rectangle) {
        return boundaries.intersects(rectangle);
    }

    private Rectangle getBoundaries(InternalTreeNode node, int i) {
        Rectangle rectangle = new Rectangle(rendererWidth, rendererHeight);
        int posX = basePoint.x + node.getLevel() * levelPadding;
        int posY = basePoint.y + i * rendererHeight;
        rectangle.setLocation(posX, posY);
        return rectangle;
    }

    private UIRenderer getRenderer() {
        //TODO add reuse of renderer feature
        try {
            return rendererClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void cleanCanvas() {
        drawer.cleanCanvas();
    }

    public void consume(Object data) {
        if (data instanceof File) {
            setRoot(new JSONStructureProvider().parseData(data, eventManager));
        }
    }

    private void dispatchEvent(TreeEventType eventType) {
        eventManager.dispatchEvent(new TreeEvent(eventType));
    }

    public InternalTreeNode getLastVisibleChild() {
        return currentlyShowingItems.size() > 0 ? ((ModelRendererPair)currentlyShowingItems.get(currentlyShowingItems.size() - 1)).getInternalTreeNode() : null;
    }
}
