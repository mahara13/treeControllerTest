package com.vm.test.oo11;

public class LevelFour_21 {
    public int run() {
        int[] arr = new int[2];
        try {
            arr[0] = new LevelFive_40().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr[2];
    }
}
