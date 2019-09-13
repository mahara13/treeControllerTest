package com.vm.test.oo;

public class LevelFour {
    public int run() {

        int [] arr =  new int[2];
        arr[0] = new LevelFive().run();
        return arr[2];
    }
}
