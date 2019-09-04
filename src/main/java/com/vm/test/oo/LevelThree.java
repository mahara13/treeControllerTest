package com.vm.test.oo;

public class LevelThree {
    public int run() {
        //For arithmetic exception test
        int d = 1;
        if (Math.random() <= 0.9) {
            try {
                throw new Exception("random exc 11");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Math.random() <= 0.5) {
            try {
                throw new Exception("random exc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Math.random() <= 0.9) {
            try {
                throw new Exception("random exc 12");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Math.random() <= 0.9) {
            try {
                throw new Exception("random exc 15");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (Math.random() <= 0.5) {
            try {
                throw new Exception("random exc 9");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Math.random() <= 0.5) {
            d = new LevelFour().run();
        }

        try {
            d = new LevelFour().run();
        } catch (Exception e) {
        }

        d--;
        return 10 / d;
    }
}
