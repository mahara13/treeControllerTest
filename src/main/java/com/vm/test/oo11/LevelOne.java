package com.vm.test.oo11;

public class LevelOne {
    public int run() {
        return new LevelTwo().run() + 1;
    }
}
