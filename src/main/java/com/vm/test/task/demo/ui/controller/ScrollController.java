package com.vm.test.task.demo.ui.controller;

import lombok.Data;

@Data
public class ScrollController {
    private boolean isVisible = false;
    private boolean isVisibleChanged = false;
    public void setIsVisible(boolean newIsVisible) {
        if (newIsVisible != isVisible) {
            isScrollLengthChanged = true;
            isVisible = newIsVisible;
        }
    }

    private int minimalBarLength = 5;

    private double scrollPositionPercent = 0;
    private boolean isScrollPositionPercentChanged = false;
    public void setScrollPositionPercent(double newScrollPositionPercent) {
        if (newScrollPositionPercent != scrollPositionPercent) {
            isScrollPositionPercentChanged = true;
            scrollPositionPercent = newScrollPositionPercent;
        }
    }

    private int scrollTrackLength = 0;
    private boolean isScrollTrailLengthChanged = false;
    public void setScrollTrackLength(int newScrollTrackLength) {
        if (newScrollTrackLength != scrollTrackLength) {
            isScrollTrailLengthChanged = true;
            scrollTrackLength = newScrollTrackLength;
        }
    }

    private int scrollToCoverLength = 0;
    private boolean isScrollToCoverLengthChanged = false;
    public void setScrollToCoverLength(int newScrollToCoverLength) {
        if (newScrollToCoverLength != scrollToCoverLength) {
            isScrollToCoverLengthChanged = true;
            scrollToCoverLength = newScrollToCoverLength;
        }
    }

    private int scrollLength = 0;
    private boolean isScrollLengthChanged = false;
    public void setScrollLength(int newScrollLength) {
        if (newScrollLength != scrollLength) {
            isScrollLengthChanged = true;
            scrollLength = newScrollLength;
        }
    }

    public ScrollController() {

    }

    public boolean calculate(int newScrollTrackLength,
                             int newScrollToCoverLength) {
        setIsVisible(newScrollToCoverLength > newScrollTrackLength);
        if (isVisible()) {
            int scrollLengthCandidate = (int) (newScrollTrackLength * (double)newScrollTrackLength / (double) newScrollToCoverLength);
            scrollLengthCandidate = scrollLengthCandidate < minimalBarLength ? minimalBarLength : scrollLengthCandidate;
            setScrollLength(scrollLengthCandidate);

            setScrollToCoverLength(newScrollToCoverLength);
            setScrollTrackLength(newScrollTrackLength);
        }

        boolean isChanged = isVisibleChanged || isScrollLengthChanged || isScrollToCoverLengthChanged || isScrollTrailLengthChanged;
        return isChanged;
    }

    public int getScrollPosition() {
        return (int)(scrollPositionPercent*(scrollTrackLength - scrollLength));
    }

    public boolean calculate(int shifting) {
        double newPercent = getScrollPositionPercent() + (double) shifting / (double) (getScrollTrackLength() - getScrollLength());
        newPercent = newPercent < 0 ? 0 : newPercent;
        newPercent = newPercent > 1 ? 1: newPercent;
        setScrollPositionPercent(newPercent);
        return isScrollPositionPercentChanged();
    }

    public void changesApplied() {
        isVisibleChanged = false;
        isScrollLengthChanged = false;
        isScrollToCoverLengthChanged = false;
        isScrollTrailLengthChanged = false;
    }

    public int getBasePointShifting() {
        return (int)(-1 * (scrollToCoverLength - scrollTrackLength) * scrollPositionPercent);
    }
}
