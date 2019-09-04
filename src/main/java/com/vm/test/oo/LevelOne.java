package com.vm.test.oo;

public class LevelOne {
    public int run() {
        return new LevelTwo().run() + 1;
    }
}
