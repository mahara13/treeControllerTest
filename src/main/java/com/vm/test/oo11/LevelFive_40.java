package com.vm.test.oo11;

import com.vm.test.oo11.exception.TestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class LevelFive_40 {
    private static final Logger log = LoggerFactory.getLogger(LevelFive_40.class);

    public static void main(String[] args) {
        Pattern p = Pattern.compile("Drill down into");
        String markdown = "*Drill down into* [Event Analysis](https://app.overops.com/tale.html?event=UzM5NDEwIzI4MCMz&source=43&timeframe=custom&from=23-Oct-19-11-29&to=24-Oct-19-11-29)\n" +
                "*Stack trace:*\n" +
                "> at LevelFour_21.run\n" +
                "> at LevelThree.run\n" +
                "> at LevelTwo.run\n" +
                "> at LevelOne.run\n" +
                "> at TestControllerApplication.lambda$main$0" ;
        boolean b = p.matcher(markdown).find();
        System.out.println(p.matcher(markdown).find());
    }

    public int run() throws Exception {
        int s = 3;

        try {
            if (s== 3 || true) throw new TestException("Opss swallowed exception ");
        } catch (Exception e) {}

        try {
            if (s== 3 || true) throw new IllegalMonitorStateException("Opss 09 ");
        } catch (Exception e) {
            log.error("[Test exception] Unable to do something", e);
            throw new Exception(e);
        }

        int k = new LevelSix_21().run();

        return k;
    }
}
