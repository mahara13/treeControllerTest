package com.vm.test.oo;

public class LevelThree {
    public int run() {
        int d = new LevelFour().run();
        d--;
        return 10 / d;
    }
}
